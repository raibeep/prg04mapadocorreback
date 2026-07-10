package br.com.ifba.mapadocorreapi.produto.service;

import br.com.ifba.mapadocorreapi.produto.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ProdutoIService {
    public Produto save (Produto produto);

    public Produto findById (Long id);

    public Page<Produto> findAll (Pageable pageable);

    public Produto update (Long id, Produto produto);

    public void delete (Long id);

    List<Produto> findByNegocioId(Long negocioId);
}
