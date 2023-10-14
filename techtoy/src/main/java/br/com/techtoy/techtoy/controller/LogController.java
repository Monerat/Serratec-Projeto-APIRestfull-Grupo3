package br.com.techtoy.techtoy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.techtoy.techtoy.service.LogService;
import br.com.techtoy.techtoy.model.Log;

@RestController
@RequestMapping("/api/logs")
public class LogController {
    
    @Autowired
    private LogService logService;

    //Create
    @PostMapping
    public ResponseEntity<Log> adicionar(@RequestBody Log log){
        return ResponseEntity
        .status(201)
        .body(logService.adicionar(log));
    }

    
    //Read
    @GetMapping
    public ResponseEntity<List<Log>> obterTodos(){
        return ResponseEntity
            .status(200)
            .body(logService.obterTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Log> obterPorId(@PathVariable Long id){
        return ResponseEntity
            .status(200)
            .body(logService.obterPorId(id));
    }

    //Update
    @PutMapping("/{id}")
    public ResponseEntity<Log> atualizar(@PathVariable Long id, @RequestBody Log log){
        return ResponseEntity
            .status(200)
            .body(logService.atualizar(id, log)); 
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        logService.deletar(id);
        return ResponseEntity
            .status(204)
            .build();
    }

}
