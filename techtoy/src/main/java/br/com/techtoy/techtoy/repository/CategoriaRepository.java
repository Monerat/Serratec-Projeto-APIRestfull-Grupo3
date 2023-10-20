package br.com.techtoy.techtoy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.techtoy.techtoy.model.Categoria;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByNome(String nome);
}