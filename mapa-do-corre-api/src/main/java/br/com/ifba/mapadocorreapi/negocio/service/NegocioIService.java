package br.com.ifba.mapadocorreapi.negocio.service;

import br.com.ifba.mapadocorreapi.negocio.entity.Negocio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NegocioIService {
    public Negocio save(Negocio negocio);

    Page<Negocio> findAll(Pageable pageable);

    Negocio findById(Long id);

    Negocio update(Long id, Negocio negocio);

    void delete(Long id);
}
