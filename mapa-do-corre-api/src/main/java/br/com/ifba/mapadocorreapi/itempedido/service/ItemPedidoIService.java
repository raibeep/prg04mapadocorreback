package br.com.ifba.mapadocorreapi.itempedido.service;

import br.com.ifba.mapadocorreapi.itempedido.entity.ItemPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemPedidoIService {

    ItemPedido save(ItemPedido itemPedido);

    ItemPedido findById(Long id);

    Page<ItemPedido> findAll(Pageable pageable);

    ItemPedido update(Long id, ItemPedido itemPedido);

    void delete(Long id);

}