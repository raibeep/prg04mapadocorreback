package br.com.ifba.mapadocorreapi.itempedido.service;

import br.com.ifba.mapadocorreapi.enums.StatusPedido;
import br.com.ifba.mapadocorreapi.infrastructure.exception.BusinessException;
import br.com.ifba.mapadocorreapi.itempedido.entity.ItemPedido;
import br.com.ifba.mapadocorreapi.itempedido.repository.ItemPedidoRepository;
import br.com.ifba.mapadocorreapi.pedido.entity.Pedido;
import br.com.ifba.mapadocorreapi.pedido.repository.PedidoRepository;
import br.com.ifba.mapadocorreapi.pedido.service.PedidoIService;
import br.com.ifba.mapadocorreapi.produto.entity.Produto;
import br.com.ifba.mapadocorreapi.produto.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemPedidoService implements ItemPedidoIService{
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoIService pedidoService;

    @Override
    @Transactional
    public ItemPedido save(ItemPedido item){

        Pedido pedido = pedidoRepository.findById(
                item.getPedido().getId()
        ).orElseThrow(() ->
                new BusinessException("Pedido não encontrado.")
        );

        Produto produto = produtoRepository.findById(
                item.getProduto().getId()
        ).orElseThrow(() ->
                new BusinessException("Produto não encontrado.")
        );

        if(pedido.getStatus()!= StatusPedido.PENDENTE){

            throw new BusinessException(
                    "Só é possível adicionar itens em pedidos pendentes."
            );

        }

        item.setPedido(pedido);
        item.setProduto(produto);
        item.setPrecoUnitario(produto.getPreco());
        ItemPedido salvo = itemPedidoRepository.save(item);
        pedido.getItens().add(salvo);
        pedidoService.atualizarValorTotal(pedido);

        return salvo;
    }

    @Override
    @Transactional
    public ItemPedido update(Long id, ItemPedido novo){

        ItemPedido item = findById(id);

        if(item.getPedido().getStatus()!=StatusPedido.PENDENTE){

            throw new BusinessException(
                    "Não é possível alterar itens de um pedido confirmado."
            );

        }

        item.setQuantidade(novo.getQuantidade());
        ItemPedido salvo = itemPedidoRepository.save(item);
        pedidoService.atualizarValorTotal(item.getPedido());

        return salvo;
    }

    @Override
    @Transactional
    public void delete(Long id){

        ItemPedido item = findById(id);
        Pedido pedido = item.getPedido();

        itemPedidoRepository.delete(item);

        pedido.getItens().remove(item);

        pedidoService.atualizarValorTotal(pedido);
    }

    @Override
    public ItemPedido findById(Long id){

        return itemPedidoRepository.findById(id)

                .orElseThrow(()->
                        new BusinessException("Item não encontrado.")
                );

    }

    @Override
    public Page<ItemPedido> findAll(Pageable pageable){

        return itemPedidoRepository.findAll(pageable);
    }
}
