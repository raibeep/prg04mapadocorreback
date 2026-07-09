package br.com.ifba.mapadocorreapi.endereco.repository;

import br.com.ifba.mapadocorreapi.endereco.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
