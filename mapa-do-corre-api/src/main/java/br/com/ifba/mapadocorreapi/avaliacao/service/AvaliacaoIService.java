package br.com.ifba.mapadocorreapi.avaliacao.service;

import br.com.ifba.mapadocorreapi.avaliacao.dto.AvaliacaoGetResponseDto;
import br.com.ifba.mapadocorreapi.avaliacao.entity.Avaliacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AvaliacaoIService {
    public Avaliacao save(Avaliacao avaliacao);

    public Avaliacao findById(Long id);

    Page<Avaliacao> findAll(Pageable pageable);

    Avaliacao update(Long id, Avaliacao avaliacao);

    void delete(Long id);

    Page<AvaliacaoGetResponseDto> findByNegocioId(Long negocioId, Pageable pageable);
}
