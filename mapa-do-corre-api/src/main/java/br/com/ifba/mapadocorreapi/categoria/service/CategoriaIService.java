package br.com.ifba.mapadocorreapi.categoria.service;

import br.com.ifba.mapadocorreapi.categoria.entity.Categoria;

import java.util.List;

public interface CategoriaIService {

    Categoria save(Categoria categoria);

    List<Categoria> findAll();

    Categoria findById(Long id);

    Categoria update(Long id, Categoria categoria);

    void delete(Long id);
}