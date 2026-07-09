package br.com.ifba.mapadocorreapi.endereco.controller;

import br.com.ifba.mapadocorreapi.endereco.dto.EnderecoGetResponseDto;
import br.com.ifba.mapadocorreapi.endereco.dto.EnderecoPostRequestDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface EnderecoIController {

    ResponseEntity<?> save(@Valid EnderecoPostRequestDto dto);

    ResponseEntity<Page<EnderecoGetResponseDto>> findAll(Pageable pageable);

    ResponseEntity<?> findById(Long id);

    ResponseEntity<?> update(Long id, @Valid EnderecoPostRequestDto dto);

    ResponseEntity<?> delete(Long id);

}