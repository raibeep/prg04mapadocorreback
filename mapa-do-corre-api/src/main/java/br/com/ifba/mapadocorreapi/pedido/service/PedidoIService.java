package br.com.ifba.mapadocorreapi.pedido.service;

import br.com.ifba.mapadocorreapi.pedido.entity.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PedidoIService {
    Pedido save(Pedido pedido);

    Pedido update(Long id, Pedido pedido);

    Pedido confirmarPedido(Long id);

    Pedido cancelarPedido(Long id);

    Page<Pedido> findAll(Pageable pageable);

    Pedido findById(Long id);

    void delete(Long id);

    void atualizarValorTotal(Pedido pedido);
}
