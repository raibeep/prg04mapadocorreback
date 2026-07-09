package br.com.ifba.mapadocorreapi.produto.controller;

import br.com.ifba.mapadocorreapi.produto.dto.ProdutoGetResponseDto;
import br.com.ifba.mapadocorreapi.produto.dto.ProdutoPostRequestDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProdutoIController {

    ResponseEntity<?> save(@RequestBody @Valid ProdutoPostRequestDto produtoPostRequestDto);

    ResponseEntity<Page<ProdutoGetResponseDto>> findAll(Pageable pageable);

    ResponseEntity<?> findById(@PathVariable Long id);

    ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody @Valid ProdutoPostRequestDto produtoPostRequestDto
    );

    ResponseEntity<?> delete(@PathVariable Long id);
}