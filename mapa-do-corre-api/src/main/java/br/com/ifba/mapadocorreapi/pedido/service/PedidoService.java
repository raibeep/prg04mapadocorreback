package br.com.ifba.mapadocorreapi.pedido.service;

import br.com.ifba.mapadocorreapi.cliente.entity.Cliente;
import br.com.ifba.mapadocorreapi.cliente.repository.ClienteRepository;
import br.com.ifba.mapadocorreapi.enums.StatusPedido;
import br.com.ifba.mapadocorreapi.infrastructure.exception.BusinessException;
import br.com.ifba.mapadocorreapi.pedido.entity.Pedido;
import br.com.ifba.mapadocorreapi.pedido.repository.PedidoRepository;
import br.com.ifba.mapadocorreapi.usuario.entity.Usuario;
import br.com.ifba.mapadocorreapi.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class PedidoService implements PedidoIService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    public Pedido save(Pedido pedido) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new BusinessException("Usuário não encontrado."));

        Cliente cliente = clienteRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() ->
                        new BusinessException("Cliente não encontrado."));

        pedido.setCliente(cliente);

        pedido.setCriadoEm(new Date());
        pedido.setStatus(StatusPedido.PENDENTE);
        pedido.setValorTotal(BigDecimal.ZERO);

        return pedidoRepository.save(pedido);
    }

    @Override
    public Page<Pedido> findAll(Pageable pageable) {
        return pedidoRepository.findAll(pageable);
    }

    @Override
    public Pedido findById(Long id) {
        return pedidoRepository.findById(id).orElseThrow(() ->
                new BusinessException("Pedido não encontrado!"));
    }

    @Override
    @Transactional
    public Pedido update(Long id, Pedido pedido) {
        Pedido pedidoExistente = findById(id);

        if (pedidoExistente.getStatus() != StatusPedido.PENDENTE) {
            throw new BusinessException("Não é possível alterar um pedido que não esteja PENDENTE.");
        }

        pedidoExistente.setValorTotal(pedido.getValorTotal());

        return pedidoRepository.save(pedidoExistente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Pedido pedido = findById(id);

        if (pedido.getStatus() != StatusPedido.PENDENTE && pedido.getStatus() != StatusPedido.CANCELADO) {
            throw new BusinessException("Apenas pedidos pendentes ou cancelados podem ser excluídos.");
        }

        pedidoRepository.delete(pedido);
    }

    @Override
    @Transactional
    public Pedido confirmarPedido(Long id) {
        Pedido pedido = findById(id);

        if (pedido.getStatus() != StatusPedido.PENDENTE) {
            throw new BusinessException("Apenas pedidos PENDENTES podem ser confirmados.");
        }

        pedido.setStatus(StatusPedido.CONFIRMADO);
        return pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public Pedido cancelarPedido(Long id) {
        Pedido pedido = findById(id);

        if (pedido.getStatus() == StatusPedido.ENTREGUE) {
            throw new BusinessException("Não é possível cancelar um pedido que já foi ENTREGUE.");
        }

        pedido.setStatus(StatusPedido.CANCELADO);
        return pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public void atualizarValorTotal(Pedido pedido){

        BigDecimal total = pedido.getItens().stream()

                .map(item ->
                        item.getPrecoUnitario()
                                .multiply(
                                        BigDecimal.valueOf(item.getQuantidade())
                                )
                )

                .reduce(BigDecimal.ZERO, BigDecimal::add);

        pedido.setValorTotal(total);

        pedidoRepository.save(pedido);
    }
}
