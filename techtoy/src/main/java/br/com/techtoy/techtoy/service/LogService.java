package br.com.techtoy.techtoy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.techtoy.techtoy.model.Log;
import br.com.techtoy.techtoy.repository.LogRepository;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    //CRUD

    //Create
    public Log adicionar(Log log){
        log.setId(0);
        return logRepository.save(log);
    }
    
    //Read
    public List<Log> obterTodos(){
        return logRepository.findAll();
    }

    
    public Log obterPorId(Long id){
        Optional<Log> log = logRepository.findById(id);

        if(log.isEmpty()){
            throw new RuntimeException("Log não foi encontrado na base com o Id: "+id);
        }
        return log.get();
    }

    //Update
    public Log atualizar(Long id, Log log){
        obterPorId(id);
        log.setId(id);
        return logRepository.save(log);
    }

    //Delete
    public void deletar(Long id){
        obterPorId(id);
        logRepository.deleteById(id);
    }

}
