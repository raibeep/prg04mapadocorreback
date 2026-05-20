package br.com.ifba.mapadocorreapi.service;

import br.com.ifba.mapadocorreapi.entity.Categoria;

import java.util.List;

public interface CategoriaIService {

    Categoria save(Categoria categoria);

    List<Categoria> findAll();

    Categoria findById(Long id);

    Categoria updateCategoria(Long id, Categoria categoria);

    void deleteCategoria(Long id);
}