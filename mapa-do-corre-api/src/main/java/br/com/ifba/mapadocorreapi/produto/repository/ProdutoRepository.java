package br.com.ifba.mapadocorreapi.produto.repository;

import br.com.ifba.mapadocorreapi.produto.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findByNomeAndNegocioId(String nome, Long negocioId);
}
