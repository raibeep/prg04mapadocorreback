package br.com.ifba.mapadocorreapi.endereco.service;

import br.com.ifba.mapadocorreapi.endereco.entity.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EnderecoIService {
    Endereco save(Endereco endereco);

    Endereco findById(Long id);

    Page<Endereco> findAll(Pageable pageable);

    Page<Endereco> findByClienteId(Long clienteId, Pageable pageable);

    Endereco update(Long id, Endereco endereco);

    void delete(Long id);
}