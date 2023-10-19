package br.com.techtoy.techtoy.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.techtoy.techtoy.dto.log.LogRequestDTO;
import br.com.techtoy.techtoy.dto.log.LogResponseDTO;
import br.com.techtoy.techtoy.model.Log;
import br.com.techtoy.techtoy.model.Usuario;
import br.com.techtoy.techtoy.model.Enum.EnumLog;
import br.com.techtoy.techtoy.model.Enum.EnumTipoEntidade;
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
    @Transactional
    public LogResponseDTO adicionar(LogRequestDTO logRequestDTO, EnumLog tipoAcao, EnumTipoEntidade tipoEntidade,
                                    String valorOriginal, String valorAtual){
        Log log = modelMapper.map(logRequestDTO, Log.class);
        
        //Setar usuario fixo até pode usar autenticacao
        Usuario usuario = new Usuario(1l);
    
        //Setar valores que a gente puxou
        log.setId(0);
        log.setTipoAcao(tipoAcao);
        log.setTipoEntidade(tipoEntidade);
        log.setValorAtual(valorAtual);
        log.setValorOriginal(valorOriginal);
        log.setUsuario(usuario);
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
    @Transactional
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

    //Fazer o Mapper
    public String mapearObjetoParaString(Object object){
        ObjectMapper objectMapper = new ObjectMapper();
        String objectString = new String();
        
        try {
            objectString = objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return objectString;
    }

}
