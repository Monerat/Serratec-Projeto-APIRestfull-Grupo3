package br.com.techtoy.techtoy.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.techtoy.techtoy.dto.Pedido.PedidoRequestDTO;
import br.com.techtoy.techtoy.dto.Pedido.PedidoResponseDTO;
import br.com.techtoy.techtoy.model.email.Email;
import br.com.techtoy.techtoy.service.EmailService;
import br.com.techtoy.techtoy.service.PedidoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    
    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private EmailService emailService;

    //Create
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> adicionar(@RequestBody PedidoRequestDTO pedido) {
               
        return ResponseEntity
            .status(201)
            .body(pedidoService.adicionar(pedido));
    }
    
    //Read
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> obterTodos(){
        return ResponseEntity
            .status(200)
            .body(pedidoService.obterTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> obterPorId(@PathVariable Long id){
        return ResponseEntity
            .status(200)
            .body(pedidoService.obterPorId(id));
    }

    @GetMapping("/email")
    public ResponseEntity<?> testeEnvioDeEmail(){

        List<String> destinatarios = new ArrayList<>();
        destinatarios.add("lucastere10@gmail.com");
        destinatarios.add("raynan2007@gmail.com");

        String mensagem = "<h1 style=\"text-align: center;\">Pedido N&deg; 562</h1>\r\n" + //
                "<p style=\"text-align: center;\"><br>Caro cliente Jose Francisco, sua compra foi aprovada! 😁</p>\r\n" + //
                "<p style=\"text-align: center;\"><img src=\"blob:https://www.tiny.cloud/e635d795-cbe0-44cd-b71c-83e621694819\"></p>";

        Email email = new Email("Teste de email", mensagem, "grupo3.serratec.apiRestful23.2@gmail.com", destinatarios);

        emailService.enviar(email);

        return ResponseEntity.status(200).body("E-mail enviado com sucesso!!!");
    }

    //Update
    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> atualizar(@PathVariable Long id, @RequestBody PedidoRequestDTO pedido){
        
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
