package br.com.techtoy.techtoy.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.techtoy.techtoy.dto.LogDTO.LogResponseDTO;
import br.com.techtoy.techtoy.dto.LogDTO.LogRequestDTO;
import br.com.techtoy.techtoy.model.Log;
import br.com.techtoy.techtoy.model.exceptions.ResourceNotFound;
import br.com.techtoy.techtoy.repository.LogRepository;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private ModelMapper modelMapper;

    //CRUD

    //Create
    public LogResponseDTO adicionar(LogRequestDTO logRequestDTO){
        Log log = modelMapper.map(logRequestDTO, Log.class);
        log.setId(0);
        Log savedLog = logRepository.save(log);
        return modelMapper.map(savedLog, LogResponseDTO.class);
    }
    
    //Read
    public List<LogResponseDTO> obterTodos(){
        List<Log> logs = logRepository.findAll();
        return logs.stream()
                .map(log -> modelMapper.map(log, LogResponseDTO.class))
                .collect(Collectors.toList());
    }

    
    public LogResponseDTO obterPorId(Long id){
        Optional<Log> log = logRepository.findById(id);

        if(log.isEmpty()){
            throw new ResourceNotFound("Nenhum registro encontrado para o ID: "+id);
        }
        return modelMapper.map(log.get(), LogResponseDTO.class);
    }

    //Update
    public LogResponseDTO atualizar(Long id, LogRequestDTO logRequestDTO){
        obterPorId(id);
        Log log = modelMapper.map(logRequestDTO, Log.class);
        log.setId(id);
        Log updatedLog = logRepository.save(log);
        return modelMapper.map(updatedLog, LogResponseDTO.class);
    }

    //Delete
    public void deletar(Long id){
        obterPorId(id);
        logRepository.deleteById(id);
    }

}
