package br.com.ifba.mapadocorreapi.categoria.controller;

import br.com.ifba.mapadocorreapi.categoria.dto.CategoriaPostRequestDto;
import org.springframework.http.ResponseEntity;

public interface CategoriaIController {

    ResponseEntity<?> save(CategoriaPostRequestDto categoriaPostRequestDto);
    ResponseEntity<?> findAll();
    ResponseEntity<?> findById(Long id);
    ResponseEntity<?> updateCategoria(Long id, CategoriaPostRequestDto categoriaPostRequestDto);
    ResponseEntity<?> deleteCategoria(Long id);
}