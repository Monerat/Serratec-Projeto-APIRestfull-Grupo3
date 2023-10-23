package br.com.techtoy.techtoy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.techtoy.techtoy.common.ChecaValores;
import br.com.techtoy.techtoy.dto.log.LogRequestDTO;
import br.com.techtoy.techtoy.dto.pedidoItem.PedidoItemRequestDTO;
import br.com.techtoy.techtoy.dto.pedidoItem.PedidoItemResponseDTO;
import br.com.techtoy.techtoy.dto.produto.ProdutoResponseDTO;
import br.com.techtoy.techtoy.model.PedidoItem;
import br.com.techtoy.techtoy.model.Enum.EnumLog;
import br.com.techtoy.techtoy.model.Enum.EnumTipoEntidade;
import br.com.techtoy.techtoy.model.exceptions.ResourceBadRequest;
import br.com.techtoy.techtoy.model.exceptions.ResourceNotFound;
import br.com.techtoy.techtoy.repository.PedidoItemRepository;

@Service
public class PedidoItemService {

    @Autowired
    private PedidoItemRepository pedidoItemRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private LogService logService;

    @Autowired
    private ModelMapper mapper;
    // CRUD

    // Create
    @Transactional
    public PedidoItemResponseDTO adicionar(PedidoItemRequestDTO pedidoItemRequest) {
        PedidoItem pedidoItemModel = mapper.map(pedidoItemRequest, PedidoItem.class);

        if (pedidoItemModel.getAcrescimo() == null) {
            pedidoItemModel.setAcrescimo(0.00);
        }
        if (pedidoItemModel.getDesconto() == null) {
            pedidoItemModel.setDesconto(0.00);
        }
        if (pedidoItemModel.getQuantidade() == null) {
            throw new ResourceBadRequest("Favor adicionar a quantidade a ser comprada");
        }
        if (pedidoItemModel.getProduto() == null) {
            throw new ResourceBadRequest("Favor adicionar o produto a ser comprado");
        }

        ChecaValores.verificaValorDoubleMenorQueZero(pedidoItemModel.getAcrescimo());
        ChecaValores.verificaValorDoubleMenorQueZero(pedidoItemModel.getDesconto());
        ChecaValores.verificaValorInt(pedidoItemModel.getQuantidade());

        pedidoItemModel = calcularSubTotal(pedidoItemModel);
        pedidoItemModel.setId(0);
        pedidoItemRepository.save(pedidoItemModel);

        // Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();
        logService.adicionar(logService.verificarUsuarioLogado(), logRequestDTO, EnumLog.CREATE,
                EnumTipoEntidade.PEDIDOITEM, "",
                logService.mapearObjetoParaString(pedidoItemModel));

        return mapper.map(pedidoItemModel, PedidoItemResponseDTO.class);
    }

    // Read
    public List<PedidoItemResponseDTO> obterTodos() {

        List<PedidoItem> pedidoItemModel = pedidoItemRepository.findAll();
        List<PedidoItemResponseDTO> pedidoItemResponse = new ArrayList<>();

        for (PedidoItem pedidoItem : pedidoItemModel) {
            pedidoItemResponse.add(mapper.map(pedidoItem, PedidoItemResponseDTO.class));
        }
        return pedidoItemResponse;
    }

    public PedidoItemResponseDTO obterPorId(Long id) {
        ChecaValores.verificaValorLong(id);
        Optional<PedidoItem> pedidoItem = pedidoItemRepository.findById(id);

        if (pedidoItem.isEmpty()) {
            throw new ResourceNotFound("Pedido Item não foi encontrado na base com o Id: " + id);
        }
        return mapper.map(pedidoItem.get(), PedidoItemResponseDTO.class);
    }

    // update
    @Transactional
    public PedidoItemResponseDTO atualizar(Long id, PedidoItemRequestDTO pedidoItemRequest) {
        ChecaValores.verificaValorLong(id);
        PedidoItem pedidoItemBase = mapper.map(obterPorId(id), PedidoItem.class);

        PedidoItem pedidoItemModel = mapper.map(pedidoItemRequest, PedidoItem.class);

        if (pedidoItemModel.getAcrescimo() == null) {
            pedidoItemModel.setAcrescimo(pedidoItemBase.getAcrescimo());
        }
        if (pedidoItemModel.getDesconto() == null) {
            pedidoItemModel.setDesconto(pedidoItemBase.getDesconto());
        }
        if (pedidoItemModel.getQuantidade() == null) {
            pedidoItemModel.setQuantidade(pedidoItemBase.getQuantidade());
        }
        if (pedidoItemModel.getProduto() == null) {
            pedidoItemModel.setProduto(pedidoItemBase.getProduto());
        }
        if (pedidoItemModel.getPedido() == null) {
            pedidoItemModel.setPedido(pedidoItemBase.getPedido());
        }

        ChecaValores.verificaValorDoubleMenorQueZero(pedidoItemModel.getAcrescimo());
        ChecaValores.verificaValorDoubleMenorQueZero(pedidoItemModel.getDesconto());
        ChecaValores.verificaValorInt(pedidoItemModel.getQuantidade());

        pedidoItemModel = calcularSubTotal(pedidoItemModel);
        pedidoItemModel.setId(id);
        pedidoItemModel = pedidoItemRepository.save(pedidoItemModel);

        // Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();

        // Registrar Mudanças UPDATE na Auditoria
        logService.adicionar(logService.verificarUsuarioLogado(), logRequestDTO, EnumLog.UPDATE,
                EnumTipoEntidade.PEDIDOITEM,
                logService.mapearObjetoParaString(pedidoItemBase),
                logService.mapearObjetoParaString(pedidoItemModel));

        return mapper.map(pedidoItemModel, PedidoItemResponseDTO.class);
    }

    // Delete
    public void deletar(Long id) {
        //teste
        ChecaValores.verificaValorLong(id);
        obterPorId(id);
        pedidoItemRepository.deleteById(id);

        // Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();
        logService.adicionar(logService.verificarUsuarioLogado(), logRequestDTO, EnumLog.DELETE,
                EnumTipoEntidade.PEDIDOITEM, "", "");

    }

    public PedidoItem calcularSubTotal(PedidoItem pedidoItemModel) {

        ProdutoResponseDTO produto = produtoService.obterPorId(pedidoItemModel.getProduto().getId());
        double subTotal = (produto.getValorUn() - pedidoItemModel.getDesconto() + pedidoItemModel.getAcrescimo())
                * pedidoItemModel.getQuantidade();

        pedidoItemModel.setSubTotal(subTotal);

        return pedidoItemModel;
    }
}
