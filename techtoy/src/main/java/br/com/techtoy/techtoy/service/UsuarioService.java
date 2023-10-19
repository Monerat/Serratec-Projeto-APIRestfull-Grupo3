package br.com.techtoy.techtoy.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.techtoy.techtoy.dto.log.LogRequestDTO;
import br.com.techtoy.techtoy.dto.usuario.UsuarioRequestDTO;
import br.com.techtoy.techtoy.dto.usuario.UsuarioResponseDTO;
import br.com.techtoy.techtoy.model.Usuario;
import br.com.techtoy.techtoy.model.Enum.EnumLog;
import br.com.techtoy.techtoy.model.Enum.EnumTipoEntidade;
import br.com.techtoy.techtoy.model.exceptions.ResourceNotFound;
import br.com.techtoy.techtoy.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LogService logService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    EmailService emailService;

    // CRUD

    // Create
    @Transactional
    public UsuarioResponseDTO adicionar(UsuarioRequestDTO usuarioReq) {
        Usuario usuarioModel = mapper.map(usuarioReq, Usuario.class);

        usuarioModel.setId(0);
        usuarioRepository.save(usuarioModel);

        //Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();
        logService.adicionar(logRequestDTO, EnumLog.CREATE, EnumTipoEntidade.USUARIO, "", 
                    logService.mapearObjetoParaString(usuarioModel));
        

        emailService.dispararEmail("Cadastro", usuarioModel.getEmail(), usuarioModel.getNome());
        return mapper.map(usuarioModel, UsuarioResponseDTO.class);
    }

    // Read
    public List<UsuarioResponseDTO> obterTodos() {

        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream().map(usuario -> mapper.map(usuario, UsuarioResponseDTO.class))
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO obterPorId(Long id) {
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);

        if (optUsuario.isEmpty()) {
            throw new ResourceNotFound("Usuario não foi encontrado na base com o Id: " + id);
        }
        return mapper.map(optUsuario.get(), UsuarioResponseDTO.class);
    }

    // Update
    @Transactional
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO usuarioRequest) {
        Usuario usuarioBase = mapper.map(obterPorId(id), Usuario.class);

        Usuario usuarioModel = mapper.map(usuarioRequest, Usuario.class);

        usuarioRequest.setId(id);
        if (usuarioModel.getNome()==null){
            usuarioModel.setNome(usuarioBase.getNome());
        }
        if (usuarioModel.getEmail()==null){
            usuarioModel.setEmail(usuarioBase.getEmail());
        }
        if (usuarioModel.getSenha()==null){
            usuarioModel.setSenha(usuarioBase.getSenha());
        }
        if (usuarioModel.getTelefone()==null){
            usuarioModel.setTelefone(usuarioBase.getTelefone());
        }
        if (usuarioModel.getPerfil()==null){
            usuarioModel.setPerfil(usuarioBase.getPerfil());
        }

        usuarioModel = usuarioRepository.save(usuarioModel);

        //serviço de disparar email
        emailService.dispararEmail("Atualização", usuarioModel.getEmail(), usuarioModel.getNome());
        
        //Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();

        //Registrar Mudanças UPDATE na Auditoria
        logService.adicionar(logRequestDTO, EnumLog.UPDATE, EnumTipoEntidade.USUARIO, 
                    logService.mapearObjetoParaString(usuarioBase),
                    logService.mapearObjetoParaString(usuarioModel)
                    );
        
        
        return mapper.map(usuarioModel, UsuarioResponseDTO.class);
    }

    // Delete
    public void deletar(Long id) {
        obterPorId(id);
        usuarioRepository.deleteById(id);

        //Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();
        logService.adicionar(logRequestDTO, EnumLog.DELETE, EnumTipoEntidade.USUARIO, "", "");
        
    }

}
