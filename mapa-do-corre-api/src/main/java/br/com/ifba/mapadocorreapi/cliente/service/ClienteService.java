package br.com.ifba.mapadocorreapi.cliente.service;

import br.com.ifba.mapadocorreapi.cliente.entity.Cliente;
import br.com.ifba.mapadocorreapi.cliente.repository.ClienteRepository;
import br.com.ifba.mapadocorreapi.enums.TiposPerfil;
import br.com.ifba.mapadocorreapi.infrastructure.exception.BusinessException;
import br.com.ifba.mapadocorreapi.perfil.entity.Perfil;
import br.com.ifba.mapadocorreapi.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService implements ClienteIService{
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public Cliente save(Cliente cliente){

        if (cliente.getUsuario() == null) {
            throw new BusinessException("Dados de usuário não informados.");
        }

        if (usuarioRepository.existsByEmail(cliente.getUsuario().getEmail())) {
            throw new BusinessException("Email já cadastrado.");
        }

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

        if (usuarioRepository.existsByEmailAndIdNot(
                cliente.getUsuario().getEmail(),
                clienteExistente.getUsuario().getId())) {
            throw new BusinessException("Email já está em uso.");
        }

        clienteExistente.setNome(cliente.getNome());
        clienteExistente.setBio(cliente.getBio());
        clienteExistente.setCpf(cliente.getCpf());
        clienteExistente.setTelefone(cliente.getTelefone());
        clienteExistente.setFotoPerfil(cliente.getFotoPerfil());
        clienteExistente.getUsuario().setEmail(cliente.getUsuario().getEmail());

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

        if (!cliente.getUsuario().getSenha().equals(senhaAtual)) {
            throw new BusinessException("Senha atual incorreta.");
        }

        cliente.getUsuario().setSenha(novaSenha);
        clienteRepository.save(cliente);
    }
}
