package br.com.techtoy.techtoy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.techtoy.techtoy.common.ChecaValores;
import br.com.techtoy.techtoy.dto.Pedido.PedidoRequestDTO;
import br.com.techtoy.techtoy.dto.Pedido.PedidoResponseDTO;
import br.com.techtoy.techtoy.dto.log.LogRequestDTO;
import br.com.techtoy.techtoy.dto.pedidoItem.PedidoItemRequestDTO;
import br.com.techtoy.techtoy.dto.pedidoItem.PedidoItemResponseDTO;
import br.com.techtoy.techtoy.dto.produto.ProdutoRequestDTO;
import br.com.techtoy.techtoy.dto.produto.ProdutoResponseDTO;
import br.com.techtoy.techtoy.model.Pedido;
import br.com.techtoy.techtoy.model.PedidoItem;
import br.com.techtoy.techtoy.model.Usuario;
import br.com.techtoy.techtoy.model.Enum.EnumLog;
import br.com.techtoy.techtoy.model.Enum.EnumTipoEntidade;

import br.com.techtoy.techtoy.model.exceptions.OutofStockException;
import br.com.techtoy.techtoy.model.exceptions.ResourceNotFound;
import br.com.techtoy.techtoy.model.exceptions.ResourceBadRequest;

import br.com.techtoy.techtoy.repository.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoItemService pedidoItemService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private LogService logService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ModelMapper mapper;

    // CRUD

    // Create
    @Transactional
    public PedidoResponseDTO adicionar(PedidoRequestDTO pedidoRequest) {
        Pedido pedidoModel = mapper.map(pedidoRequest, Pedido.class);

        // CRIAR NOVA VARIAVEL DE PEDIDOITEMREQUESTDTO
        List<PedidoItemRequestDTO> pedidoItemRequest = pedidoRequest.getPedidoItens();

        if (pedidoModel.getFormaPagamento() == null) {
            throw new ResourceBadRequest("Favor adicionar a forma de pagamento");
        }

        pedidoModel.setId(0);
        pedidoModel.setUsuario(logService.verificarUsuarioLogado()); // precisa dessas linhas?
        pedidoModel = pedidoRepository.save(pedidoModel);

        Usuario usuarioLogado = logService.verificarUsuarioLogado();

        pedidoModel.setUsuario(usuarioLogado);
        pedidoModel = pedidoRepository.save(pedidoModel);

        // adicionar pedidoItens no pedido
        List<PedidoItemResponseDTO> itens = adicionarItens(pedidoItemRequest, pedidoModel);
        PedidoResponseDTO pedidoResponse = mapper.map(pedidoModel, PedidoResponseDTO.class);
        pedidoResponse.setPedidoItens(itens);
        pedidoModel = mapper.map(pedidoResponse, Pedido.class);

        pedidoModel = pedidoRepository.save(calcularValoresTotais(pedidoModel));

        // Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();

        logService.adicionar(usuarioLogado, logRequestDTO, EnumLog.CREATE, EnumTipoEntidade.PEDIDO, "",
                logService.mapearObjetoParaString(pedidoModel));

        diminuirEstoque(pedidoModel);
        emailService.dispararEmailPedido(usuarioLogado.getEmail(), usuarioLogado.getNome(), pedidoModel);

        pedidoResponse = mapper.map(pedidoModel, PedidoResponseDTO.class);

        return pedidoResponse;
    }

    private List<PedidoItemResponseDTO> adicionarItens(List<PedidoItemRequestDTO> pedidosRequest, Pedido pedidoModel) {

        List<PedidoItemResponseDTO> adicionadas = new ArrayList<>();

        for (PedidoItemRequestDTO pedidoItemRequest : pedidosRequest) {

            PedidoItem pedidoItem = mapper.map(pedidoItemRequest, PedidoItem.class);
            pedidoItem.setPedido(pedidoModel);
            adicionadas.add(pedidoItemService.adicionar(mapper.map(pedidoItem, PedidoItemRequestDTO.class)));
        }

        return adicionadas;

    }

    // Read
    public List<PedidoResponseDTO> obterTodos() {

        List<Pedido> pedidos = pedidoRepository.findAll();

        return pedidos.stream().map(pedido -> mapper
                .map(pedido, PedidoResponseDTO.class))
                .collect(Collectors.toList());
    }

    public PedidoResponseDTO obterPorId(Long id) {
        ChecaValores.verificaValorLong(id);

        Optional<Pedido> optpedido = pedidoRepository.findById(id);

        if (optpedido.isEmpty()) {
            throw new ResourceNotFound("Pedido não foi encontrado na base com o Id: " + id);
        }

        return mapper.map(optpedido.get(), PedidoResponseDTO.class);
    }

    // Update
    @Transactional
    public PedidoResponseDTO atualizar(Long id, PedidoRequestDTO pedidoRequest) {
        ChecaValores.verificaValorLong(id);

        Pedido pedidoBase = mapper.map(obterPorId(id), Pedido.class);
        Pedido pedidoModel = mapper.map(pedidoRequest, Pedido.class);

        if (pedidoModel.getDataPedido() == null) {
            pedidoModel.setDataPedido(pedidoBase.getDataPedido());
        }
        if (pedidoModel.getFormaPagamento() == null) {
            pedidoModel.setFormaPagamento(pedidoBase.getFormaPagamento());
        }
        if (pedidoModel.getObservacao() == null) {
            pedidoModel.setObservacao(pedidoBase.getObservacao());
        }
        if (pedidoModel.getUsuario() == null) {
            pedidoModel.setUsuario(pedidoBase.getUsuario());
        }

        pedidoModel.setId(id);
        pedidoModel.setUsuario(logService.verificarUsuarioLogado());
        pedidoModel = calcularValoresTotais(pedidoModel);
        pedidoModel = pedidoRepository.save(pedidoModel);

        // Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();

        // Registrar Mudanças UPDATE na Auditoria
        logService.adicionar(logService.verificarUsuarioLogado(), logRequestDTO, EnumLog.UPDATE,
                EnumTipoEntidade.PEDIDO,
                logService.mapearObjetoParaString(pedidoBase),
                logService.mapearObjetoParaString(pedidoModel));

        return mapper.map(pedidoModel, PedidoResponseDTO.class);
    }

    // Delete
    public void deletar(Long id) {
        ChecaValores.verificaValorLong(id);
        obterPorId(id);
        pedidoRepository.deleteById(id);

        // Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();
        logService.adicionar(logService.verificarUsuarioLogado(), logRequestDTO, EnumLog.DELETE,
                EnumTipoEntidade.PEDIDO, "", "");

    }

    public Pedido calcularValoresTotais(Pedido pedido) {

        List<PedidoItem> pedidosItens = pedido.getPedidoItens();

        Double valorTotal = 0.0;
        Double descontoTotal = 0.0;
        Double acrescimoTotal = 0.0;

        for (PedidoItem pedidoItem : pedidosItens) {
            acrescimoTotal += pedidoItem.getAcrescimo() * pedidoItem.getQuantidade();
            descontoTotal += pedidoItem.getDesconto() * pedidoItem.getQuantidade();
            valorTotal += pedidoItem.getSubTotal();
        }

        pedido.setAcrescimoTotal(acrescimoTotal);
        pedido.setDescontoTotal(descontoTotal);
        pedido.setValorTotal(valorTotal);

        return pedido;
    }

    public void diminuirEstoque(Pedido pedido) {
        Integer estoque;
        Integer quantidade;

        for (PedidoItem pedidoItem : pedido.getPedidoItens()) {
            ProdutoResponseDTO produtoAtual = produtoService.obterPorId(pedidoItem.getProduto().getId());
            estoque = produtoAtual.getEstoque();
            quantidade = pedidoItem.getQuantidade();
            estoque = estoque - quantidade;
            if (estoque < 0) {
                throw new OutofStockException("Estamos sem estoque suficiente do item " + produtoAtual.getNome());
            }

            produtoAtual.setEstoque(estoque);
            produtoService.atualizar(produtoAtual.getId(), mapper.map(produtoAtual, ProdutoRequestDTO.class));
        }
    }

}