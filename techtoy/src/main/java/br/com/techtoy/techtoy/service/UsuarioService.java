package br.com.techtoy.techtoy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.techtoy.techtoy.model.Usuario;
import br.com.techtoy.techtoy.model.exceptions.ResourceNotFound;
import br.com.techtoy.techtoy.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    //CRUD

    //Create
    public Usuario adicionar(Usuario usuario){
        usuario.setId(0);
        return usuarioRepository.save(usuario);
    }

    //Read
    public List<Usuario> obterTodos(){
        return usuarioRepository.findAll();
    }

    public Usuario obterPorId(Long id){
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if(usuario.isEmpty()){
            throw new ResourceNotFound("Usuario não foi encontrado na base com o Id: "+id);
        }
        return usuario.get();
    }

    //Update
    public Usuario atualizar(Long id, Usuario usuario){
        obterPorId(id);
        usuario.setId(id);
        return usuarioRepository.save(usuario);
    }

    //Delete
    public void deletar(Long id){
        obterPorId(id);
        usuarioRepository.deleteById(id);
    }
}
