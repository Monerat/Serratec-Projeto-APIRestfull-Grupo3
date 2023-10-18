package br.com.techtoy.techtoy.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.techtoy.techtoy.dto.produto.ProdutoRequestDTO;
import br.com.techtoy.techtoy.dto.produto.ProdutoResponseDTO;
import br.com.techtoy.techtoy.model.Produto;
import br.com.techtoy.techtoy.model.exceptions.ResourceNotFound;
import br.com.techtoy.techtoy.repository.ProdutoRepository;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ModelMapper mapper;
    //CRUD

    //Create
    @Transactional
    public ProdutoResponseDTO adicionar(ProdutoRequestDTO produtoRequest){
        Produto produtoModel = mapper.map(produtoRequest, Produto.class);

        produtoModel.setId(0);
        produtoRepository.save(produtoModel);

        return mapper.map(produtoModel, ProdutoResponseDTO.class);
    }

    //Read
    public List<ProdutoResponseDTO> obterTodos(){
        List<Produto> produtoModel = produtoRepository.findAll();
        List<ProdutoResponseDTO> produtoResponse = new ArrayList<>();

        for (Produto produto : produtoModel){
            produtoResponse.add(mapper.map(produto, ProdutoResponseDTO.class));
        }
        return produtoResponse;
    }

    public ProdutoResponseDTO obterPorId(Long id){
        Optional<Produto> produto = produtoRepository.findById(id);

        if(produto.isEmpty()){
            throw new ResourceNotFound("Produto não foi encontrado na base com o Id: "+id);
        }
        return mapper.map(produto.get(), ProdutoResponseDTO.class);
    }

    //Update
    @Transactional
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO produtoRequest){
        Produto produtoBase = mapper.map(obterPorId(id), Produto.class);

        Produto produtoModel = mapper.map(produtoRequest, Produto.class);

        produtoModel.setId(id);
        if (produtoModel.getNome()==null){
            produtoModel.setNome(produtoBase.getNome());
        }
        if (produtoModel.getObservacao()==null){
            produtoModel.setObservacao(produtoBase.getObservacao());
        }
        if (produtoModel.getValorUn()==null){
            produtoModel.setValorUn(produtoBase.getValorUn());
        }
        if (produtoModel.isAtivo()==null){
            produtoModel.setAtivo(produtoBase.isAtivo());//entrou aqui nao alterou
        }
        if(produtoModel.getCategoria()==null){
            produtoModel.setCategoria(produtoBase.getCategoria());
        }

        produtoModel = produtoRepository.save(produtoModel);
        
        return mapper.map(produtoModel, ProdutoResponseDTO.class);
    }

    //Delete
    public void deletar(Long id){
        obterPorId(id);
        produtoRepository.deleteById(id);
    }
}
