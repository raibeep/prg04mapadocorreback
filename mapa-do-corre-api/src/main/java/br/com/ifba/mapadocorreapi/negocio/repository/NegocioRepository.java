package br.com.ifba.mapadocorreapi.negocio.repository;

import br.com.ifba.mapadocorreapi.negocio.entity.Negocio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NegocioRepository extends JpaRepository<Negocio, Long> {
}
