package br.com.techtoy.techtoy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    // Criação de uma nova categoria.
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CategoriaResponseDTO> adicionar(@RequestBody CategoriaRequestDTO categoriaRequest) {
        CategoriaResponseDTO categoriaAdicionado = categoriaService.adicionar(categoriaRequest);

        return ResponseEntity
                .status(201)
                .body(categoriaAdicionado);
    }

    // Obtém todas as categorias públicas.
    @GetMapping("/public")
    public ResponseEntity<List<CategoriaResponseDTO>> obterTodosPublic() {
        return ResponseEntity
                .status(200)
                .body(categoriaService.obterTodosPublic());
    }

    // Obtém uma categoria pública por ID.
    @GetMapping("/public/{id}")
    public ResponseEntity<CategoriaResponseDTO> obterPorIdPublic(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(categoriaService.obterPorIdPublic(id));
    }

    // Obtém uma categoria pública por nome.
    @GetMapping("/public/nome/{nome}")
    public ResponseEntity<CategoriaResponseDTO> obterPorIdPublic(@PathVariable String nome) {
        return ResponseEntity
                .status(200)
                .body(categoriaService.obterPorNomePublic(nome));
    }

    // Obtém todas as categorias (restrito a administradores).
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<CategoriaResponseDTO>> obterTodos() {
        return ResponseEntity.ok(categoriaService.obterTodos());
    }

    // Obtém uma categoria por ID (restrito a administradores).
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CategoriaResponseDTO> obterPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.obterPorId(id));
    }

    // Atualização de categoria (restrito a administradores)

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable Long id,
            @RequestBody CategoriaRequestDTO categoriaRequest) {
        CategoriaResponseDTO categoriaAtualizado = categoriaService.atualizar(id, categoriaRequest);

        return ResponseEntity
                .status(200)
                .body(categoriaAtualizado);
    }

    // Exclusão de categoria (restrito a administradores)

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        categoriaService.deletar(id);

        return ResponseEntity
                .status(204)
                .build();
    }
}
