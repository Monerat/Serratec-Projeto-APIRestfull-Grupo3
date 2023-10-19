package br.com.techtoy.techtoy.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.techtoy.techtoy.dto.log.LogRequestDTO;
import br.com.techtoy.techtoy.dto.usuario.UsuarioLoginResponseDTO;
import br.com.techtoy.techtoy.dto.usuario.UsuarioRequestDTO;
import br.com.techtoy.techtoy.dto.usuario.UsuarioResponseDTO;
import br.com.techtoy.techtoy.model.Usuario;
import br.com.techtoy.techtoy.model.Enum.EnumLog;
import br.com.techtoy.techtoy.model.Enum.EnumTipoEntidade;
import br.com.techtoy.techtoy.model.exceptions.ResourceNotFound;
import br.com.techtoy.techtoy.repository.UsuarioRepository;
import br.com.techtoy.techtoy.security.JWTService;

@Service
public class UsuarioService {

    private static final String BEARER = "Bearer ";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LogService logService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    EmailService emailService;

    // CRUD

    // Create
    @Transactional
    public UsuarioResponseDTO adicionar(UsuarioRequestDTO usuarioReq) {
        Usuario usuarioModel = mapper.map(usuarioReq, Usuario.class);
        String senha =  passwordEncoder.encode(usuarioModel.getSenha());

        usuarioModel.setSenha(senha);
        usuarioModel.setId(0l);
        usuarioRepository.save(usuarioModel);

        // Fazer Auditoria
        // LogRequestDTO logRequestDTO = new LogRequestDTO();
        // logService.adicionar(logRequestDTO, EnumLog.CREATE, EnumTipoEntidade.USUARIO, "",
        //         logService.mapearObjetoParaString(usuarioModel));

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

        
        if (usuarioModel.getNome() == null) {
            usuarioModel.setNome(usuarioBase.getNome());
        }
        if (usuarioModel.getEmail() == null) {
            usuarioModel.setEmail(usuarioBase.getEmail());
        }
        if (usuarioModel.getSenha() == null) {
            usuarioModel.setSenha(usuarioBase.getSenha());
        }
        if (usuarioModel.getTelefone() == null) {
            usuarioModel.setTelefone(usuarioBase.getTelefone());
        }
        if (usuarioModel.getPerfil() == null) {
            usuarioModel.setPerfil(usuarioBase.getPerfil());
        }

        String senha =  passwordEncoder.encode(usuarioModel.getSenha());
        usuarioModel.setId(id);
        usuarioModel.setSenha(senha);
        usuarioModel = usuarioRepository.save(usuarioModel);

        // serviço de disparar email
        emailService.dispararEmail("Atualização", usuarioModel.getEmail(), usuarioModel.getNome());

        // Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();

        // Registrar Mudanças UPDATE na Auditoria
        logService.adicionar(logRequestDTO, EnumLog.UPDATE, EnumTipoEntidade.USUARIO,
                logService.mapearObjetoParaString(usuarioBase),
                logService.mapearObjetoParaString(usuarioModel));

        return mapper.map(usuarioModel, UsuarioResponseDTO.class);
    }

    // Delete
    public void deletar(Long id) {
        obterPorId(id);
        usuarioRepository.deleteById(id);

        // Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();
        logService.adicionar(logRequestDTO, EnumLog.DELETE, EnumTipoEntidade.USUARIO, "", "");

    }

    public UsuarioResponseDTO obterPorEmail(String email){
        Optional<Usuario> optUsuario =  usuarioRepository.findByEmail(email);

        return mapper.map(optUsuario.get(),UsuarioResponseDTO.class);
    }

    // Logar
    public UsuarioLoginResponseDTO logar(String email, String senha) {
        // Aqui que a autenticação acontece dentro do spring automagicamente.

        Optional<Usuario> optUsuario = usuarioRepository.findByEmail(email);

        if (optUsuario.isEmpty()) {
            throw new BadCredentialsException("Usuário ou senha invalidos");
        }

        Authentication autenticacao = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, senha, Collections.emptyList()));

        // Aqui eu passo a nova autenticação para o springSecurity cuidar pra mim.
        SecurityContextHolder.getContext().setAuthentication(autenticacao);

        // Crio o token JWT
        String token = BEARER + jwtService.gerarToken(autenticacao);

        // Pego o usuario dono do token
        UsuarioResponseDTO usuarioResponse = obterPorEmail(email);

        // Crio e devolvo o DTO esperado.
        return new UsuarioLoginResponseDTO(token, usuarioResponse);
    }
}
