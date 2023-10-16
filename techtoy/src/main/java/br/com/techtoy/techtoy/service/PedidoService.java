package br.com.techtoy.techtoy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.techtoy.techtoy.model.Pedido;
import br.com.techtoy.techtoy.repository.PedidoRepository;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;

    //CRUD

    //Create
    public Pedido adicionar(Pedido pedido){
        pedido.setId(0);
        return pedidoRepository.save(pedido);
    }

    //Read
    public List<Pedido> obterTodos(){
        return pedidoRepository.findAll();
    }

    public Pedido obterPorId(Long id){
        Optional<Pedido> pedido = pedidoRepository.findById(id);

        if(pedido.isEmpty()){
            throw new RuntimeException("Pedido não foi encontrado na base com o Id: "+id);
        }
        return pedido.get();
    }

    //Update
    public Pedido atualizar(Long id, Pedido pedido){
        obterPorId(id);
        pedido.setId(id);
        return pedidoRepository.save(pedido);
    }

    //Delete
    public void deletar(Long id){
        obterPorId(id);
        pedidoRepository.deleteById(id);
    }
}
