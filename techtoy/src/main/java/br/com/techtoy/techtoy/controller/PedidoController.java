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

import br.com.techtoy.techtoy.dto.Pedido.PedidoRequestDTO;
import br.com.techtoy.techtoy.dto.Pedido.PedidoResponseDTO;
import br.com.techtoy.techtoy.service.PedidoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // Criação de um novo pedido.
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> adicionar(@RequestBody PedidoRequestDTO pedido) {
        return ResponseEntity
                .status(201)
                .body(pedidoService.adicionar(pedido));
    }

    // Obtém todos os pedidos (restrito a administradores).
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<PedidoResponseDTO>> obterTodos() {
        return ResponseEntity
                .status(200)
                .body(pedidoService.obterTodos());
    }

    // Obtém um pedido por ID (restrito a administradores).
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PedidoResponseDTO> obterPorId(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(pedidoService.obterPorId(id));
    }

    // Atualiza um pedido por ID.
    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> atualizar(@PathVariable Long id, @RequestBody PedidoRequestDTO pedido) {
        return ResponseEntity
                .status(200)
                .body(pedidoService.atualizar(id, pedido));
    }

    // Exclusão de pedido (Restrito a administradores)

    // Deleta um pedido por ID (restrito a administradores).
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        pedidoService.deletar(id);
        return ResponseEntity
                .status(204)
                .build();
    }
}
