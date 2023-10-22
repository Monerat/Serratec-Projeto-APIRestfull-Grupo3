package br.com.techtoy.techtoy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.techtoy.techtoy.service.LogService;
import br.com.techtoy.techtoy.dto.log.LogResponseDTO;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    @Autowired
    private LogService logService;

    // Read
    @GetMapping
    public ResponseEntity<List<LogResponseDTO>> obterTodos() {
        List<LogResponseDTO> logs = logService.obterTodos();
        return ResponseEntity.ok(logs);
    }

    // Read by ID
    @GetMapping("/{id}")
    public ResponseEntity<LogResponseDTO> obterPorId(@PathVariable Long id) {
        LogResponseDTO log = logService.obterPorId(id);
        return ResponseEntity.ok(log);
    }

}
