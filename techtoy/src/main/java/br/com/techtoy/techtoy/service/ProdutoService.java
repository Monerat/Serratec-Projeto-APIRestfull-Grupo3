package br.com.techtoy.techtoy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.techtoy.techtoy.model.Produto;
import br.com.techtoy.techtoy.repository.ProdutoRepository;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    //CRUD

    //Create
    public Produto adicionar(Produto produto){
        produto.setId(0);
        return produtoRepository.save(produto);
    }

    //Read
    public List<Produto> obterTodos(){
        return produtoRepository.findAll();
    }

    public Produto obterPorId(Long id){
        Optional<Produto> produto = produtoRepository.findById(id);

        if(produto.isEmpty()){
            throw new RuntimeException("Produto não foi encontrado na base com o Id: "+id);
        }
        return produto.get();
    }

    //Update
    public Produto atualizar(Long id, Produto produto){
        obterPorId(id);
        produto.setId(id);
        return produtoRepository.save(produto);
    }

    //Delete
    public void deletar(Long id){
        obterPorId(id);
        produtoRepository.deleteById(id);
    }
}
