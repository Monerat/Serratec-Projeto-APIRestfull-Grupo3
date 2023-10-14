package br.com.techtoy.techtoy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.techtoy.techtoy.model.PedidoItem;
import br.com.techtoy.techtoy.model.Produto;
import br.com.techtoy.techtoy.repository.PedidoItemRepository;

@Service
public class PedidoItemService {
    
    @Autowired
    private PedidoItemRepository pedidoItemRepository;

       //CRUD

    //Create
    public PedidoItem adicionar(PedidoItem pedidoItem){
        pedidoItem.setId(0);
        return pedidoItemRepository.save(pedidoItem);
    }

    //Read
     public List<PedidoItem> obterTodos(){
        return pedidoItemRepository.findAll();
    }

    public PedidoItem obterPorId(Long id){
        Optional<PedidoItem> pedidoItem = pedidoItemRepository.findById(id);

        if(pedidoItem.isEmpty()){
            throw new RuntimeException("Pedido Item não foi encontrado na base com o Id: "+id);
        }
        return pedidoItem.get();
    }

    //update
    public PedidoItem atualizar(Long id, PedidoItem pedidoItem){
        obterPorId(id);
        pedidoItem.setId(id);
        return pedidoItemRepository.save(pedidoItem);
    }

    //Delete
    public void deletar(Long id){
        obterPorId(id);
        pedidoItemRepository.deleteById(id);
    }
}
