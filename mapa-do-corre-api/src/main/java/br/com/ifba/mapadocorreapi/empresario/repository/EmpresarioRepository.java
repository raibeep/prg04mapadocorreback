package br.com.ifba.mapadocorreapi.empresario.repository;

import br.com.ifba.mapadocorreapi.empresario.entity.Empresario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresarioRepository extends JpaRepository<Empresario, Long> {
}
