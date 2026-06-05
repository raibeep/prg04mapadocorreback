package br.com.ifba.mapadocorreapi.categoria.service;

import br.com.ifba.mapadocorreapi.categoria.entity.Categoria;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoriaIService {

    Categoria save(Categoria categoria);

    Page<Categoria> findAll(Pageable pageable);

    Categoria findById(Long id);

    Categoria update(Long id, Categoria categoria);

    void delete(Long id);
}