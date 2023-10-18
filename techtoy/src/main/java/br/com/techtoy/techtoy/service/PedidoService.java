package br.com.techtoy.techtoy.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.techtoy.techtoy.dto.Pedido.PedidoRequestDTO;
import br.com.techtoy.techtoy.dto.Pedido.PedidoResponseDTO;
import br.com.techtoy.techtoy.model.Pedido;
import br.com.techtoy.techtoy.repository.PedidoRepository;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;


    @Autowired
    private ModelMapper mapper;

    //CRUD

    //Create
    @Transactional
    public PedidoResponseDTO adicionar(PedidoRequestDTO pedidoRequest){
        Pedido pedidoModel = mapper.map(pedidoRequest, Pedido.class);

        pedidoModel.setId(0);
        pedidoModel = pedidoRepository.save(pedidoModel);
        
        return mapper.map(pedidoModel, PedidoResponseDTO.class);     
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
        
        obterPorId(id);
        
        pedidoRequest.setId(id);

        Pedido pedidoModel = pedidoRepository.save(mapper.map(pedidoRequest, Pedido.class));
       
        return mapper.map(pedidoModel, PedidoResponseDTO.class);
    }


    //Delete
    public void deletar(Long id){
        obterPorId(id);
        pedidoRepository.deleteById(id);
    }
}
