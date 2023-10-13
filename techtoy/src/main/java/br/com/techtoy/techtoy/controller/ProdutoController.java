package br.com.techtoy.techtoy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.techtoy.techtoy.model.Produto;
import br.com.techtoy.techtoy.service.ProdutoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    
    @Autowired
    private ProdutoService produtoService;

    //Create
    @PostMapping
    public ResponseEntity<Produto> adicionar(@RequestBody Produto produto) {
        return ResponseEntity
            .status(201)
            .body(produtoService.adicionar(produto));
    }

    //Read
    @GetMapping
    public ResponseEntity<List<Produto>> obterTodos(){
        return ResponseEntity
            .status(200)
            .body(produtoService.obterTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> obterPorId(@PathVariable Long id){
        return ResponseEntity
            .status(200)
            .body(produtoService.obterPorId(id));
    }

    //Update
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto produto){
        return ResponseEntity
            .status(200)
            .body(produtoService.atualizar(id, produto)); 
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        produtoService.deletar(id);
        return ResponseEntity
            .status(204)
            .build();
    }
}
