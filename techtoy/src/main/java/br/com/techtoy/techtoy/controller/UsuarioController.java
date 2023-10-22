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

    /**
     * Cria um novo usuário.
     *
     * @param usuario O objeto UsuarioRequestDTO contendo os detalhes do usuário.
     * @return Um ResponseEntity contendo o UsuarioResponseDTO recém-criado.
     */
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> adicionar(@RequestBody UsuarioRequestDTO usuario) {
        return ResponseEntity
                .status(201)
                .body(usuarioService.adicionar(usuario));
    }

    /**
     * Obtém todos os usuários (requer autorização ADMIN).
     *
     * @return Um ResponseEntity contendo uma lista de UsuarioResponseDTO.
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UsuarioResponseDTO>> obterTodos() {
        return ResponseEntity
                .status(200)
                .body(usuarioService.obterTodos());
    }

    /**
     * Obtém um usuário por ID (requer autorização ADMIN).
     *
     * @param id O ID do usuário.
     * @return Um ResponseEntity contendo o UsuarioResponseDTO correspondente.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> obterPorId(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(usuarioService.obterPorId(id));
    }

    /**
     * Atualiza um usuário por ID (requer autorização ADMIN).
     *
     * @param id      O ID do usuário a ser atualizado.
     * @param usuario O objeto UsuarioRequestDTO contendo os novos detalhes do
     *                usuário.
     * @return Um ResponseEntity contendo o UsuarioResponseDTO atualizado.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable Long id, @RequestBody UsuarioRequestDTO usuario) {
        return ResponseEntity
                .status(200)
                .body(usuarioService.atualizar(id, usuario));
    }

    /**
     * Deleta um usuário por ID (requer autorização ADMIN).
     *
     * @param id O ID do usuário a ser excluído.
     * @return Um ResponseEntity indicando o sucesso da operação.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity
                .status(204)
                .build();
    }

    /**
     * Realiza o login de um usuário.
     *
     * @param usuariologinRequest O objeto UsuarioLoginRequestDTO contendo as
     *                            credenciais de login.
     * @return Um ResponseEntity contendo o UsuarioLoginResponseDTO com as
     *         informações do usuário logado.
     */
    @PostMapping("/login")
    public ResponseEntity<UsuarioLoginResponseDTO> logar(@RequestBody UsuarioLoginRequestDTO usuariologinRequest) {

        UsuarioLoginResponseDTO usuarioLogado = usuarioService.logar(usuariologinRequest.getEmail(),
                usuariologinRequest.getSenha());

        return ResponseEntity
                .status(200)
                .body(usuarioLogado);
    }

    @GetMapping("/public")
    public ResponseEntity<UsuarioResponseDTO> obterPorIdPublicoLogado() {
        return ResponseEntity
                .status(200)
                .body(usuarioService.obterUsuarioLogado());
    }
}