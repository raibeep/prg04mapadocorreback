package br.com.ifba.mapadocorreapi.cliente.service;

import br.com.ifba.mapadocorreapi.avaliacao.entity.Avaliacao;
import br.com.ifba.mapadocorreapi.cliente.entity.Cliente;
import br.com.ifba.mapadocorreapi.pedido.entity.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteIService {
    public Cliente save(Cliente cliente);

    Page<Cliente> findAll(Pageable pageable);

    Cliente findById(Long id);

    Cliente update(Long id, Cliente cliente);

    void delete(Long id);

    public void updateSenha(Long id, String senhaAtual, String novaSenha);

    public Avaliacao avaliarNegocio(Long clienteId, Long negocioId, Avaliacao avaliacao);

    Pedido realizarPedido(Long clienteId, Pedido pedido);}
