package br.com.techtoy.techtoy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.techtoy.techtoy.model.PedidoItem;
import br.com.techtoy.techtoy.model.Produto;
import br.com.techtoy.techtoy.service.PedidoItemService;
import br.com.techtoy.techtoy.service.ProdutoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/pedidoItem")
public class PedidoItemController {
    
    @Autowired
    private PedidoItemService pedidoItemService;

    //Create
    @PostMapping
    public ResponseEntity<PedidoItem> adicionar(@RequestBody PedidoItem pedidoItem) {
        return ResponseEntity
            .status(201)
            .body(pedidoItemService.adicionar(pedidoItem));
    }

    //Read
    @GetMapping
    public ResponseEntity<List<PedidoItem>> obterTodos(){
        return ResponseEntity
            .status(200)
            .body(pedidoItemService.obterTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoItem> obterPorId(@PathVariable Long id){
        return ResponseEntity
            .status(200)
            .body(pedidoItemService.obterPorId(id));
    }

      //Update
      @PutMapping("/{id}")
      public ResponseEntity<PedidoItem> atualizar(@PathVariable Long id, @RequestBody PedidoItem pedidoItem){
          return ResponseEntity
              .status(200)
              .body(pedidoItemService.atualizar(id, pedidoItem)); 
      }
  
      //Delete
      @DeleteMapping("/{id}")
      public ResponseEntity<?> deletar(@PathVariable Long id){
          pedidoItemService.deletar(id);
          return ResponseEntity
              .status(204)
              .build();
      }

}
