package br.com.techtoy.techtoy.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.techtoy.techtoy.dto.usuario.UsuarioRequestDTO;
import br.com.techtoy.techtoy.dto.usuario.UsuarioResponseDTO;
import br.com.techtoy.techtoy.model.Usuario;
import br.com.techtoy.techtoy.model.exceptions.ResourceNotFound;
import br.com.techtoy.techtoy.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper mapper;

    // CRUD

    // Create
    @Transactional
    public UsuarioResponseDTO adicionar(UsuarioRequestDTO usuarioReq) {

        Usuario usuarioModel = mapper.map(usuarioReq, Usuario.class);
        usuarioModel.setId(0);
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
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO usuarioReq) {
        obterPorId(id);
        usuarioReq.setId(id);

        Usuario usuarioModel = usuarioRepository.save(mapper.map(usuarioReq, Usuario.class));

        return mapper.map(usuarioModel, UsuarioResponseDTO.class);
    }

    // Delete
    public void deletar(Long id) {
        obterPorId(id);
        usuarioRepository.deleteById(id);
    }
}
