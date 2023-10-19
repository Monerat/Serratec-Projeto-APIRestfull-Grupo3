package br.com.techtoy.techtoy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.techtoy.techtoy.dto.Pedido.PedidoRequestDTO;
import br.com.techtoy.techtoy.dto.Pedido.PedidoResponseDTO;
import br.com.techtoy.techtoy.dto.log.LogRequestDTO;
import br.com.techtoy.techtoy.dto.pedidoItem.PedidoItemRequestDTO;
import br.com.techtoy.techtoy.dto.pedidoItem.PedidoItemResponseDTO;
import br.com.techtoy.techtoy.model.Pedido;
import br.com.techtoy.techtoy.model.PedidoItem;
import br.com.techtoy.techtoy.model.Enum.EnumLog;
import br.com.techtoy.techtoy.model.Enum.EnumTipoEntidade;
import br.com.techtoy.techtoy.repository.PedidoRepository;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoItemService pedidoItemService;

    @Autowired
    private LogService logService;

    @Autowired
    private ModelMapper mapper;

    //CRUD

    //Create
    @Transactional
    public PedidoResponseDTO adicionar(PedidoRequestDTO pedidoRequest){
        Pedido pedidoModel = mapper.map(pedidoRequest, Pedido.class);

        //CRIAR NOVA VARIAVEL DE PEDIDOITEMREQUESTDTO
        List<PedidoItemRequestDTO> pedidoItemRequest = pedidoRequest.getPedidoItens();

        pedidoModel.setId(0);
        pedidoModel = pedidoRepository.save(pedidoModel);

        //adicionar pedidoItens no pedido
        List<PedidoItemResponseDTO> itens = adicionarItens(pedidoItemRequest, pedidoModel);
        PedidoResponseDTO pedidoResponse = mapper.map(pedidoModel, PedidoResponseDTO.class); 
        pedidoResponse.setPedidoItens(itens);

        //Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();
        logService.adicionar(logRequestDTO, EnumLog.CREATE, EnumTipoEntidade.PEDIDO, "", 
                    logService.mapearObjetoParaString(pedidoModel));

        
        return pedidoResponse;
    }

    private List<PedidoItemResponseDTO> adicionarItens(List<PedidoItemRequestDTO> pedidosRequest, Pedido pedidoModel){
        
        List<PedidoItemResponseDTO> adicionadas = new ArrayList<>();

        for(PedidoItemRequestDTO pedidoItemRequest : pedidosRequest){
            PedidoItem pedidoItem = mapper.map(pedidoItemRequest, PedidoItem.class);
            pedidoItem.setPedido(pedidoModel);
            //pedidoItem = pedidoItemService.adicionar(mapper.map(pedidoItem, PedidoItemRequestDTO.class));
            adicionadas.add(pedidoItemService.adicionar(mapper.map(pedidoItem, PedidoItemRequestDTO.class)));
        }

        return adicionadas;

    }  

    //Read
    public List<PedidoResponseDTO> obterTodos(){

        List<Pedido> pedidos = pedidoRepository.findAll();

        return pedidos.stream().map(pedido -> mapper
        .map(pedido, PedidoResponseDTO.class))
        .collect(Collectors.toList());
    }

    public PedidoResponseDTO obterPorId(Long id){

        Optional<Pedido> optpedido = pedidoRepository.findById(id);

        if(optpedido.isEmpty()){
            throw new RuntimeException("Pedido não foi encontrado na base com o Id: "+id);
        }
        
        return mapper.map(optpedido.get(), PedidoResponseDTO.class);
    }

    //Update
    @Transactional
    public PedidoResponseDTO atualizar(Long id, PedidoRequestDTO pedidoRequest){
        Pedido pedidoBase = mapper.map(obterPorId(id),Pedido.class);
        Pedido pedidoModel = mapper.map(pedidoRequest, Pedido.class);
        //obterPorId(id);
        
        pedidoModel.setId(id);
        if (pedidoModel.getDataPedido()==null){
            pedidoModel.setDataPedido(pedidoBase.getDataPedido());
        }
        if (pedidoModel.getFormaPagamento()==null){
            pedidoModel.setFormaPagamento(pedidoBase.getFormaPagamento());
        }
        if (pedidoModel.getObservacao()==null){
            pedidoModel.setObservacao(pedidoBase.getObservacao());
        }
        if (pedidoModel.getUsuario()==null){
            pedidoModel.setUsuario(pedidoBase.getUsuario());
        }

        pedidoModel = pedidoRepository.save(pedidoModel);
       
        //Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();

        //Registrar Mudanças UPDATE na Auditoria
        logService.adicionar(logRequestDTO, EnumLog.UPDATE, EnumTipoEntidade.PEDIDO, 
                    logService.mapearObjetoParaString(pedidoBase),
                    logService.mapearObjetoParaString(pedidoModel)
                    );
        

        return mapper.map(pedidoModel, PedidoResponseDTO.class);
    }


    //Delete
    public void deletar(Long id){
        obterPorId(id);
        pedidoRepository.deleteById(id);
        
        //Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();
        logService.adicionar(logRequestDTO, EnumLog.DELETE, EnumTipoEntidade.PEDIDO, "", "");

    }
}
