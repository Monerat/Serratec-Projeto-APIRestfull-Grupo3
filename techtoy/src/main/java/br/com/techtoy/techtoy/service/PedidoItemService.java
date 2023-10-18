package br.com.techtoy.techtoy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.techtoy.techtoy.dto.pedidoItem.PedidoItemRequestDTO;
import br.com.techtoy.techtoy.dto.pedidoItem.PedidoItemResponseDTO;
import br.com.techtoy.techtoy.model.PedidoItem;
import br.com.techtoy.techtoy.model.exceptions.ResourceNotFound;
import br.com.techtoy.techtoy.repository.PedidoItemRepository;

@Service
public class PedidoItemService {
    
    @Autowired
    private PedidoItemRepository pedidoItemRepository;

    @Autowired
    private ModelMapper mapper;
    //CRUD

    //Create
    public PedidoItemResponseDTO adicionar(PedidoItemRequestDTO pedidoItemRequest){
        PedidoItem pedidoItemModel = mapper.map(pedidoItemRequest, PedidoItem.class);

        pedidoItemModel.setId(0);
        pedidoItemRepository.save(pedidoItemModel);
        
        return mapper.map(pedidoItemModel, PedidoItemResponseDTO.class);
    }

    //Read
     public List<PedidoItemResponseDTO> obterTodos(){
        
        List<PedidoItem> pedidoItemModel = pedidoItemRepository.findAll();
        List<PedidoItemResponseDTO> pedidoItemResponse = new ArrayList<>();

        for (PedidoItem pedidoItem : pedidoItemModel){
            pedidoItemResponse.add(mapper.map(pedidoItem, PedidoItemResponseDTO.class));
        }
        return pedidoItemResponse;
    }

    public PedidoItemResponseDTO obterPorId(Long id){
        Optional<PedidoItem> pedidoItem = pedidoItemRepository.findById(id);

        if(pedidoItem.isEmpty()){
            throw new ResourceNotFound("Pedido Item não foi encontrado na base com o Id: "+id);
        }
            return mapper.map(pedidoItem.get(), PedidoItemResponseDTO.class);
    }

    //update
    public PedidoItemResponseDTO atualizar(Long id, PedidoItemRequestDTO pedidoItemRequest){
        obterPorId(id);
        
        PedidoItem pedidoItemModel = mapper.map(pedidoItemRequest, PedidoItem.class);

        pedidoItemModel.setId(id);
        pedidoItemModel = pedidoItemRepository.save(pedidoItemModel);
        
        return mapper.map(pedidoItemModel, PedidoItemResponseDTO.class);
    }

    //Delete
    public void deletar(Long id){
        obterPorId(id);
        pedidoItemRepository.deleteById(id);
    }
}
