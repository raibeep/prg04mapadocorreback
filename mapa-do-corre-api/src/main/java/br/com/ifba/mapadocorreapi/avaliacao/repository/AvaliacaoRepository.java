package br.com.ifba.mapadocorreapi.avaliacao.repository;

import br.com.ifba.mapadocorreapi.avaliacao.entity.Avaliacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    Page<Avaliacao> findByNegocioId(Long negocioId, Pageable pageable);
}
