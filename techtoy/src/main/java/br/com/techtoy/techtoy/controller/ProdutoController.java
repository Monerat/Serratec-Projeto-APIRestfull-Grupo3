package br.com.techtoy.techtoy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.techtoy.techtoy.dto.produto.ProdutoRequestDTO;
import br.com.techtoy.techtoy.dto.produto.ProdutoResponseDTO;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProdutoResponseDTO> adicionar(@RequestBody ProdutoRequestDTO produto) {
        return ResponseEntity
            .status(201)
            .body(produtoService.adicionar(produto));
    }

    //Read
    //public
    @GetMapping("/public")
    public ResponseEntity<List<ProdutoResponseDTO>> obterTodosPublic(){
        return ResponseEntity
            .status(200)
            .body(produtoService.obterTodosPublic());
    }

    @GetMapping("/public/{id}")
    public ResponseEntity<ProdutoResponseDTO> obterPorIdPublic(@PathVariable Long id){
        return ResponseEntity
            .status(200)
            .body(produtoService.obterPorIdPublic(id));
    }

    //privado
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<ProdutoResponseDTO>> obterTodos(){
        return ResponseEntity
            .status(200)
            .body(produtoService.obterTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProdutoResponseDTO> obterPorId(@PathVariable Long id){
        return ResponseEntity
            .status(200)
            .body(produtoService.obterPorId(id));
    }

    //Update
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Long id, @RequestBody ProdutoRequestDTO produto){
        return ResponseEntity
            .status(200)
            .body(produtoService.atualizar(id, produto)); 
    }

    //Delete
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        produtoService.deletar(id);
        return ResponseEntity
            .status(204)
            .build();
    }
}
