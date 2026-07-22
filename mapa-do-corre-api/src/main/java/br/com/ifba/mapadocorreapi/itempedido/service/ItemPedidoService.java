package br.com.ifba.mapadocorreapi.itempedido.service;

import br.com.ifba.mapadocorreapi.empresario.entity.Empresario;
import br.com.ifba.mapadocorreapi.empresario.repository.EmpresarioRepository;
import br.com.ifba.mapadocorreapi.enums.StatusItemPedido;
import br.com.ifba.mapadocorreapi.enums.StatusPedido;
import br.com.ifba.mapadocorreapi.infrastructure.exception.BusinessException;
import br.com.ifba.mapadocorreapi.itempedido.entity.ItemPedido;
import br.com.ifba.mapadocorreapi.itempedido.repository.ItemPedidoRepository;
import br.com.ifba.mapadocorreapi.pedido.entity.Pedido;
import br.com.ifba.mapadocorreapi.pedido.repository.PedidoRepository;
import br.com.ifba.mapadocorreapi.pedido.service.PedidoIService;
import br.com.ifba.mapadocorreapi.produto.entity.Produto;
import br.com.ifba.mapadocorreapi.produto.repository.ProdutoRepository;
import br.com.ifba.mapadocorreapi.usuario.entity.Usuario;
import br.com.ifba.mapadocorreapi.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemPedidoService implements ItemPedidoIService{
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoIService pedidoService;
    private final UsuarioRepository usuarioRepository;
    private final EmpresarioRepository empresarioRepository;

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

    @Override
    public List<ItemPedido> findByNegocio(Long negocioId) {
        return itemPedidoRepository.findByProduto_Negocio_Id(negocioId);
    }

    @Override
    @Transactional
    public ItemPedido confirmarItem(Long id) {

        System.out.println("ID recebido: " + id);

        ItemPedido item = buscarEValidarDono(id);

        if (item.getStatus() != StatusItemPedido.PENDENTE) {
            throw new BusinessException("Apenas itens PENDENTES podem ser confirmados.");
        }

        item.setStatus(StatusItemPedido.CONFIRMADO);

        ItemPedido salvo = itemPedidoRepository.save(item);

        atualizarStatusPedido(salvo.getPedido());

        return salvo;
    }

    @Override
    @Transactional
    public ItemPedido cancelarItem(Long id) {

        ItemPedido item = buscarEValidarDono(id);

        if (item.getStatus() != StatusItemPedido.PENDENTE) {
            throw new BusinessException("Apenas itens PENDENTES podem ser cancelados.");
        }

        item.setStatus(StatusItemPedido.CANCELADO);

        ItemPedido salvo = itemPedidoRepository.save(item);

        atualizarStatusPedido(salvo.getPedido());

        return salvo;
    }

    private void atualizarStatusPedido(Pedido pedido) {

        List<ItemPedido> itens = itemPedidoRepository.findByPedido(pedido);

        boolean todosCancelados = itens.stream()
                .allMatch(i -> i.getStatus() == StatusItemPedido.CANCELADO);

        boolean existePendente = itens.stream()
                .anyMatch(i -> i.getStatus() == null || i.getStatus() == StatusItemPedido.PENDENTE);

        if (todosCancelados) {
            pedido.setStatus(StatusPedido.CANCELADO);
        } else if (!existePendente) {
            pedido.setStatus(StatusPedido.CONFIRMADO);
        } else {
            pedido.setStatus(StatusPedido.PENDENTE);
        }

        pedidoRepository.save(pedido);

    }

    private ItemPedido buscarEValidarDono(Long id) {

        ItemPedido item = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Item de pedido não encontrado."));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("Authentication: " + authentication);
        System.out.println("Principal: " + authentication.getPrincipal());
        System.out.println("Name: " + authentication.getName());

        String email = authentication.getName();

        System.out.println("Email recebido: " + email);

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado."));

        Empresario empresario = empresarioRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new BusinessException("Empresário não encontrado."));

        Long donoDoProdutoId = item.getProduto().getNegocio().getDono().getId();

        if (!donoDoProdutoId.equals(empresario.getId())) {
            throw new BusinessException("Você não tem permissão para alterar este item.");
        }

        return item;
    }
}
