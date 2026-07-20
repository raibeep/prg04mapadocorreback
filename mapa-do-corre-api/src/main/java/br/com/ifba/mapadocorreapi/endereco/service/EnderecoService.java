package br.com.ifba.mapadocorreapi.endereco.service;

import br.com.ifba.mapadocorreapi.endereco.entity.Endereco;
import br.com.ifba.mapadocorreapi.endereco.repository.EnderecoRepository;
import br.com.ifba.mapadocorreapi.infrastructure.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnderecoService implements EnderecoIService {

    private final EnderecoRepository enderecoRepository;

    @Override
    public Endereco save(Endereco endereco) {
        if (endereco.getCliente() != null && endereco.getEmpresario() != null) {
            throw new BusinessException("O endereço não pode pertencer ao cliente e ao empresário ao mesmo tempo.");
        }

        if (endereco.getCliente() != null && endereco.getNegocio() != null) {
            throw new BusinessException("O endereço não pode pertencer ao cliente e ao negócio ao mesmo tempo.");
        }

        if (endereco.getEmpresario() != null && endereco.getNegocio() != null) {
            throw new BusinessException("O endereço não pode pertencer ao empresário e ao negócio ao mesmo tempo.");
        }

        if (endereco.getCliente() == null &&
                endereco.getEmpresario() == null &&
                endereco.getNegocio() == null) {

            throw new BusinessException("O endereço deve pertencer a um cliente, empresário ou negócio.");
        }
        return enderecoRepository.save(endereco);
    }

    @Override
    public Endereco findById(Long id) {

        return enderecoRepository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Endereço não encontrado."));
    }

    @Override
    public Page<Endereco> findAll(Pageable pageable) {

        return enderecoRepository.findAll(pageable);
    }

    @Override
    public Endereco update(Long id, Endereco endereco) {

        Endereco enderecoExistente = findById(id);

        enderecoExistente.setRua(endereco.getRua());
        enderecoExistente.setBairro(endereco.getBairro());
        enderecoExistente.setCidade(endereco.getCidade());
        enderecoExistente.setEstado(endereco.getEstado());

        return enderecoRepository.save(enderecoExistente);
    }

    @Override
    public void delete(Long id) {

        enderecoRepository.delete(findById(id));
    }

    @Override
    public Page<Endereco> findByClienteId(Long clienteId, Pageable pageable) {
        return enderecoRepository.findByClienteId(clienteId, pageable);
    }

}