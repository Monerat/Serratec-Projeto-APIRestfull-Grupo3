package br.com.techtoy.techtoy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.techtoy.techtoy.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria,Long> {
    
}