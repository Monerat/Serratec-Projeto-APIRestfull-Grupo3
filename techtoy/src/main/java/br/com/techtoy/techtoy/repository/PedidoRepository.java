package br.com.techtoy.techtoy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.techtoy.techtoy.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
