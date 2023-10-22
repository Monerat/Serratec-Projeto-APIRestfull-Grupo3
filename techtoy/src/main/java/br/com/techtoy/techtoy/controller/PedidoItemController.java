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

import br.com.techtoy.techtoy.dto.pedidoItem.PedidoItemRequestDTO;
import br.com.techtoy.techtoy.dto.pedidoItem.PedidoItemResponseDTO;
import br.com.techtoy.techtoy.service.PedidoItemService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/pedidoItens")
public class PedidoItemController {

    @Autowired
    private PedidoItemService pedidoItemService;

    // Criação de um novo item de pedido.
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PedidoItemResponseDTO> adicionar(@RequestBody PedidoItemRequestDTO pedidoItem) {
        return ResponseEntity
                .status(201)
                .body(pedidoItemService.adicionar(pedidoItem));
    }

    // Obtém todos os itens de pedido (restrito a administradores).
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<PedidoItemResponseDTO>> obterTodos() {
        return ResponseEntity
                .status(200)
                .body(pedidoItemService.obterTodos());
    }

    // Obtém um item de pedido por ID (restrito a administradores).
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PedidoItemResponseDTO> obterPorId(@PathVariable Long id) {
        return ResponseEntity
                .status(200)
                .body(pedidoItemService.obterPorId(id));
    }

    // Atualização de item de pedido

    // Atualiza um item de pedido por ID.
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PedidoItemResponseDTO> atualizar(@PathVariable Long id,
            @RequestBody PedidoItemRequestDTO pedidoItem) {
        return ResponseEntity
                .status(200)
                .body(pedidoItemService.atualizar(id, pedidoItem));
    }

    // Exclusão de item de pedido (Restrito a administradores)

    // Deleta um item de pedido por ID (restrito a administradores).
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        pedidoItemService.deletar(id);
        return ResponseEntity
                .status(204)
                .build();
    }
}
