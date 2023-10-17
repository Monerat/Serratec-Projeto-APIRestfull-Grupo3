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
import br.com.techtoy.techtoy.dto.LogDTO.LogRequestDTO;
import br.com.techtoy.techtoy.dto.LogDTO.LogResponseDTO;
import br.com.techtoy.techtoy.model.Log;

@RestController
@RequestMapping("/api/logs")
public class LogController {
    
    @Autowired
    private LogService logService;

    // verificar necessidade do create log

    // //Create
    // @PostMapping
    // public ResponseEntity<LogResponseDTO> adicionar(@RequestBody LogRequestDTO logRequestDTO) {
    //     LogResponseDTO logResponseDTO = logService.adicionar(logRequestDTO);
    //     return ResponseEntity.ok(logResponseDTO);
    // }

    
    //Read
    @GetMapping
    public ResponseEntity<List<LogResponseDTO>> obterTodos() {
        List<LogResponseDTO> logs = logService.obterTodos();
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LogResponseDTO> obterPorId(@PathVariable Long id) {
        LogResponseDTO log = logService.obterPorId(id);
        return ResponseEntity.ok(log);
    }

    // verificar necessidade do metodo PUT em log

    // @PutMapping("/{id}")
    // public ResponseEntity<LogResponseDTO> atualizar(@PathVariable Long id, @RequestBody LogRequestDTO logRequestDTO) {
    //     LogResponseDTO updatedLog = logService.atualizar(id, logRequestDTO);
    //     return ResponseEntity.ok(updatedLog);
    // }


    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        logService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
