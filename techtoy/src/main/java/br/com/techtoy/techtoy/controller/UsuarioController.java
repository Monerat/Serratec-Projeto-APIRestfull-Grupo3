package br.com.techtoy.techtoy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.techtoy.techtoy.dto.usuario.UsuarioLoginRequestDTO;
import br.com.techtoy.techtoy.dto.usuario.UsuarioLoginResponseDTO;
import br.com.techtoy.techtoy.dto.usuario.UsuarioRequestDTO;
import br.com.techtoy.techtoy.dto.usuario.UsuarioResponseDTO;
import br.com.techtoy.techtoy.service.UsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin("*")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    //Create
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> adicionar(@RequestBody UsuarioRequestDTO usuario) {
        return ResponseEntity
            .status(201)
            .body(usuarioService.adicionar(usuario));
    }

    //Read
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UsuarioResponseDTO>> obterTodos(){
        return ResponseEntity
            .status(200)
            .body(usuarioService.obterTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> obterPorId(@PathVariable Long id){
        return ResponseEntity
            .status(200)
            .body(usuarioService.obterPorId(id));
    }

    //Update
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable Long id, @RequestBody UsuarioRequestDTO usuario){
        return ResponseEntity
            .status(200)
            .body(usuarioService.atualizar(id, usuario)); 
    }

    //Delete
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        usuarioService.deletar(id);
        return ResponseEntity
            .status(204)
            .build();
    }

    //login
    @PostMapping("/login")
    public ResponseEntity<UsuarioLoginResponseDTO> logar(@RequestBody UsuarioLoginRequestDTO usuariologinRequest){

        UsuarioLoginResponseDTO usuarioLogado = usuarioService.logar(usuariologinRequest.getEmail(), usuariologinRequest.getSenha());

        return ResponseEntity
            .status(200)
            .body(usuarioLogado);
    }
}
