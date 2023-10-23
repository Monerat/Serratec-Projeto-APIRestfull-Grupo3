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
import org.springframework.web.client.ResourceAccessException;

import br.com.techtoy.techtoy.common.ChecaValores;
import br.com.techtoy.techtoy.dto.log.LogRequestDTO;
import br.com.techtoy.techtoy.dto.usuario.UsuarioLoginResponseDTO;
import br.com.techtoy.techtoy.dto.usuario.UsuarioRequestDTO;
import br.com.techtoy.techtoy.dto.usuario.UsuarioResponseDTO;
import br.com.techtoy.techtoy.model.Usuario;
import br.com.techtoy.techtoy.model.Enum.EnumLog;
import br.com.techtoy.techtoy.model.Enum.EnumTipoEntidade;

import br.com.techtoy.techtoy.model.exceptions.ResourceBadRequest;
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

        if (usuarioModel.getNome() == null) {
            throw new ResourceBadRequest("Você não inseriu o nome do usuário, que é um campo que não pode ser nulo");
        }

        if (usuarioModel.getTelefone() == null) {
            throw new ResourceBadRequest(
                    "Você não inseriu o telefone do usuário, que é um campo que não pode ser nulo");
        }

        if (usuarioModel.getEmail() == null) {
            throw new ResourceBadRequest(
                    "Você não inseriu o email do usuário, que é um campo que não pode ser nulo");
        }

        if (usuarioModel.getSenha() == null) {
            throw new ResourceBadRequest(
                    "Você não inseriu a senha do usuário, que é um campo que não pode ser nulo");
        }

        if (usuarioModel.getPerfil() == null) {
            throw new ResourceBadRequest(
                    "Você não inseriu o tipo do perfil do usuário, que é um campo que não pode ser nulo");
        }

        if (usuarioRepository.findByEmail(usuarioModel.getEmail()).isPresent()) {
            throw new ResourceBadRequest("O email informado já está cadastrado no sistema.");
        }

        String senha = passwordEncoder.encode(usuarioModel.getSenha());

        usuarioModel.setSenha(senha);
        usuarioModel.setId(0l);
        usuarioRepository.save(usuarioModel);

        // //Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();
        Usuario usuarioCadastrado = mapper.map(obterPorEmail(usuarioReq.getEmail()), Usuario.class);

        logService.adicionar(usuarioCadastrado, logRequestDTO, EnumLog.CREATE, EnumTipoEntidade.USUARIO, "",
                logService.mapearObjetoParaString(usuarioCadastrado));

        // emailService.dispararEmail("Cadastro", usuarioModel.getEmail(),
        // usuarioModel.getNome());
        return mapper.map(usuarioModel, UsuarioResponseDTO.class);
    }

    // Read
    public List<UsuarioResponseDTO> obterTodos() {

        List<Usuario> usuarios = usuarioRepository.findAll();

        return usuarios.stream().map(usuario -> mapper.map(usuario, UsuarioResponseDTO.class))
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO obterPorId(Long id) {
        ChecaValores.verificaValorLong(id);
        Optional<Usuario> optUsuario = usuarioRepository.findById(id);

        if (optUsuario.isEmpty()) {
            throw new ResourceNotFound("Usuario não foi encontrado na base com o Id: " + id);
        }
        return mapper.map(optUsuario.get(), UsuarioResponseDTO.class);
    }

    // Update
    @Transactional
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO usuarioRequest) {
        ChecaValores.verificaValorLong(id);
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

        String senha = passwordEncoder.encode(usuarioModel.getSenha());
        usuarioModel.setId(id);
        usuarioModel.setSenha(senha);
        usuarioModel = usuarioRepository.save(usuarioModel);

        // serviço de disparar email
        emailService.dispararEmail("Atualização", usuarioModel.getEmail(), usuarioModel.getNome());

        // Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();

        // Registrar Mudanças UPDATE na Auditoria
        logService.adicionar(logService.verificarUsuarioLogado(), logRequestDTO, EnumLog.UPDATE,
                EnumTipoEntidade.USUARIO,
                logService.mapearObjetoParaString(usuarioBase),
                logService.mapearObjetoParaString(usuarioModel));

        return mapper.map(usuarioModel, UsuarioResponseDTO.class);
    }

    // Delete
    public void deletar(Long id) {
        ChecaValores.verificaValorLong(id);
        obterPorId(id);
        usuarioRepository.deleteById(id);

        // Fazer Auditoria
        LogRequestDTO logRequestDTO = new LogRequestDTO();
        logService.adicionar(logService.verificarUsuarioLogado(), logRequestDTO, EnumLog.DELETE,
                EnumTipoEntidade.USUARIO, "", "");

    }

    public UsuarioResponseDTO obterPorEmail(String email) {
        Optional<Usuario> optUsuario = usuarioRepository.findByEmail(email);

        return mapper.map(optUsuario.get(), UsuarioResponseDTO.class);
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

    // quem sou eu
    public UsuarioResponseDTO obterUsuarioLogado() {

        if (!jwtService.isAuthenticated()) {
            throw new ResourceAccessException("Usuário não está authenticado!");
        }
        return mapper.map(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal(), UsuarioResponseDTO.class);
    }
}