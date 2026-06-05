package br.com.ifba.mapadocorreapi.categoria.controller;

import br.com.ifba.mapadocorreapi.categoria.dto.CategoriaGetResponseDto;
import br.com.ifba.mapadocorreapi.categoria.dto.CategoriaPostRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoriaIController {

    ResponseEntity<?> save(CategoriaPostRequestDto categoriaPostRequestDto);
    ResponseEntity<Page<CategoriaGetResponseDto>> findAll(Pageable pageable);
    ResponseEntity<?> findById(Long id);
    ResponseEntity<?> update(Long id, CategoriaPostRequestDto categoriaPostRequestDto);
    ResponseEntity<?> delete(Long id);
}