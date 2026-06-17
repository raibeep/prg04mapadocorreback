package br.com.ifba.mapadocorreapi.avaliacao.repository;

import br.com.ifba.mapadocorreapi.avaliacao.entity.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
}
