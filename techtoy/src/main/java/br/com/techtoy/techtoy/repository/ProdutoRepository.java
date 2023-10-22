package br.com.techtoy.techtoy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.techtoy.techtoy.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
