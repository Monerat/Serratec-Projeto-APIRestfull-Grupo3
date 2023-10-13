package br.com.techtoy.techtoy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.techtoy.techtoy.model.Categoria;
import br.com.techtoy.techtoy.service.CategoriaService;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    
    @Autowired
    private CategoriaService categoriaService;

    //Create
    @PostMapping
    public ResponseEntity<Categoria> adicionar(@RequestBody Categoria categoria){
        return ResponseEntity
            .status(201)
            .body(categoriaService.adicionar(categoria));
    }
    //Read
    @GetMapping
    public ResponseEntity<List<Categoria>> obterTodos(){
        return ResponseEntity
            .status(200)
            .body(categoriaService.obterTodos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obterPorId(@PathVariable Long id){
        return ResponseEntity 
            .status(200)
            .body(categoriaService.obterPorId(id));
    }
    //Update
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> atualizar(@PathVariable Long id, @RequestBody Categoria categoria){
        return ResponseEntity
            .status(200)
            .body(categoriaService.atualizar(id, categoria));
    }
    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        categoriaService.deletar(id);
        return ResponseEntity
            .status(204)
            .build();
    }
}

