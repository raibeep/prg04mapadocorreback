package br.com.ifba.mapadocorreapi.cliente.service;

import br.com.ifba.mapadocorreapi.avaliacao.entity.Avaliacao;
import br.com.ifba.mapadocorreapi.avaliacao.repository.AvaliacaoRepository;
import br.com.ifba.mapadocorreapi.cliente.entity.Cliente;
import br.com.ifba.mapadocorreapi.cliente.repository.ClienteRepository;
import br.com.ifba.mapadocorreapi.enums.StatusPedido;
import br.com.ifba.mapadocorreapi.enums.TiposPerfil;
import br.com.ifba.mapadocorreapi.infrastructure.exception.BusinessException;
import br.com.ifba.mapadocorreapi.negocio.entity.Negocio;
import br.com.ifba.mapadocorreapi.negocio.repository.NegocioRepository;
import br.com.ifba.mapadocorreapi.pedido.entity.Pedido;
import br.com.ifba.mapadocorreapi.pedido.repository.PedidoRepository;
import br.com.ifba.mapadocorreapi.perfil.entity.Perfil;
import br.com.ifba.mapadocorreapi.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ClienteService implements ClienteIService{
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final PedidoRepository pedidoRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    private final NegocioRepository negocioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Cliente save(Cliente cliente) {
        if (cliente.getUsuario() == null) {
            throw new BusinessException("Dados de usuário não informados.");
        }

        if (usuarioRepository.existsByEmail(cliente.getUsuario().getEmail())) {
            throw new BusinessException("Email já cadastrado.");
        }

        // Criptografa a senha antes de salvar
        cliente.getUsuario().setSenha(passwordEncoder.encode(cliente.getUsuario().getSenha()));

        Perfil perfil = new Perfil();
        perfil.setNivelAcesso(TiposPerfil.CLIENTE);
        cliente.getUsuario().setPerfil(perfil);

        return clienteRepository.save(cliente);
    }

    @Override
    public Page<Cliente> findAll(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }

    @Override
    public Cliente findById(Long id) {
        return clienteRepository.findById(id).orElseThrow(() ->
            new BusinessException("Cliente não encontrado!"));
    }

    @Override
    @Transactional
    public Cliente update(Long id, Cliente cliente) {
        Cliente clienteExistente = findById(id);

        clienteExistente.setNome(cliente.getNome());
        clienteExistente.setBio(cliente.getBio());
        clienteExistente.setTelefone(cliente.getTelefone());
        clienteExistente.setFotoPerfil(cliente.getFotoPerfil());

        return clienteRepository.save(clienteExistente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Cliente cliente = findById(id);
        clienteRepository.delete(cliente);
    }

    @Transactional
    public void updateSenha(Long id, String senhaAtual, String novaSenha) {
        Cliente cliente = findById(id);

        // Compara com matches porque a senha no banco está criptografada
        if (!passwordEncoder.matches(senhaAtual, cliente.getUsuario().getSenha())) {
            throw new BusinessException("Senha atual incorreta.");
        }

        cliente.getUsuario().setSenha(passwordEncoder.encode(novaSenha));
        clienteRepository.save(cliente);
    }

    @Override
    @Transactional
    public Avaliacao avaliarNegocio(Long clienteId, Long negocioId, Avaliacao avaliacao) {
        Cliente cliente = findById(clienteId);

        Negocio negocio = negocioRepository.findById(negocioId)
                .orElseThrow(() -> new BusinessException("Negócio não encontrado."));

        if (avaliacao.getNota() < 1 || avaliacao.getNota() > 5) {
            throw new BusinessException("A nota deve ser entre 1 e 5.");
        }

        avaliacao.setAutor(cliente.getUsuario());
        avaliacao.setNegocio(negocio);
        avaliacao.setCriadoEm(new Date());

        return avaliacaoRepository.save(avaliacao);
    }

    @Override
    @Transactional
    public Pedido realizarPedido(Long clienteId, Long negocioId, Pedido pedido) {
        Cliente cliente = findById(clienteId);

        Negocio negocio = negocioRepository.findById(negocioId)
                .orElseThrow(() -> new BusinessException("Negócio não encontrado."));

        pedido.setCliente(cliente.getUsuario());
        pedido.setNegocio(negocio);
        pedido.setStatus(StatusPedido.PENDENTE);
        pedido.setCriadoEm(new Date());

        return pedidoRepository.save(pedido);
    }
}
