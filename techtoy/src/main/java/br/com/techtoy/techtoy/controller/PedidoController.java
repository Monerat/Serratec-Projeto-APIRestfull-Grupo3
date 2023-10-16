package br.com.techtoy.techtoy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.techtoy.techtoy.model.Pedido;
import br.com.techtoy.techtoy.service.PedidoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    
    @Autowired
    private PedidoService pedidoService;

    //Create
    @PostMapping
    public ResponseEntity<Pedido> adicionar(@RequestBody Pedido pedido) {
        return ResponseEntity
            .status(201)
            .body(pedidoService.adicionar(pedido));
    }

    //Read
    @GetMapping
    public ResponseEntity<List<Pedido>> obterTodos(){
        return ResponseEntity
            .status(200)
            .body(pedidoService.obterTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obterPorId(@PathVariable Long id){
        return ResponseEntity
            .status(200)
            .body(pedidoService.obterPorId(id));
    }

    //Update
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizar(@PathVariable Long id, @RequestBody Pedido pedido){
        return ResponseEntity
            .status(200)
            .body(pedidoService.atualizar(id, pedido)); 
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        pedidoService.deletar(id);
        return ResponseEntity
            .status(204)
            .build();
    }
}
