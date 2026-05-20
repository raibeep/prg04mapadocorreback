package br.com.ifba.mapadocorreapi.controller;

import br.com.ifba.mapadocorreapi.entity.Categoria;
import org.springframework.http.ResponseEntity;

public interface CategoriaIController {

    ResponseEntity<?> save(Categoria categoria);
    ResponseEntity<?> findAll();
    ResponseEntity<?> findById(Long id);
    ResponseEntity<?> updateCategoria(Long id, Categoria categoria);
    void deleteCategoria(Long id);
}