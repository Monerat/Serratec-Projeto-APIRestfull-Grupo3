package br.com.techtoy.techtoy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.techtoy.techtoy.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    
}