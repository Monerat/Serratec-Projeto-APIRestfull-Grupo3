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

import br.com.techtoy.techtoy.dto.categoria.CategoriaRequestDTO;
import br.com.techtoy.techtoy.dto.categoria.CategoriaResponseDTO;
import br.com.techtoy.techtoy.service.CategoriaService;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    
    @Autowired
    private CategoriaService categoriaService;

    //Create
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> adicionar(@RequestBody CategoriaRequestDTO categoriaRequest){
        CategoriaResponseDTO categoriaAdicionado = categoriaService.adicionar(categoriaRequest);

        return ResponseEntity
            .status(201)
            .body(categoriaAdicionado);
    }
    //Read
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> obterTodos(){
        return ResponseEntity.ok(categoriaService.obterTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> obterPorId(@PathVariable Long id){
        return ResponseEntity.ok(categoriaService.obterPorId(id));
    }
    //Update
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable Long id, @RequestBody CategoriaRequestDTO categoriaRequest){
        CategoriaResponseDTO categoriaAtualizado = categoriaService.atualizar(id, categoriaRequest);
        
        return ResponseEntity
            .status(200)
            .body(categoriaAtualizado);
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

