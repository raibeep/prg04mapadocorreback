package br.com.ifba.mapadocorreapi.pedido.repository;

import br.com.ifba.mapadocorreapi.pedido.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
