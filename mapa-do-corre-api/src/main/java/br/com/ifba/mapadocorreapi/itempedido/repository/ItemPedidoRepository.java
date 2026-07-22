package br.com.ifba.mapadocorreapi.itempedido.repository;

import br.com.ifba.mapadocorreapi.enums.StatusItemPedido;
import br.com.ifba.mapadocorreapi.itempedido.entity.ItemPedido;
import br.com.ifba.mapadocorreapi.pedido.entity.Pedido;
import br.com.ifba.mapadocorreapi.produto.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

    List<ItemPedido> findByPedido(Pedido pedido);
    boolean existsByProduto(Produto produto);

    List<ItemPedido> findByProduto_Negocio_Id(Long negocioId);
    List<ItemPedido> findByProduto_Negocio_IdAndStatus(Long negocioId, StatusItemPedido status);
}