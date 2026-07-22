package br.com.ifba.mapadocorreapi.negocio.repository;

import br.com.ifba.mapadocorreapi.negocio.entity.Negocio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NegocioRepository extends JpaRepository<Negocio, Long> {
    Optional<Negocio> findByNome(String nome);
    boolean existsByDonoId(Long donoId);
    Optional<Negocio> findByDonoId(Long donoId);
}