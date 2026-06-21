package br.com.ifba.mapadocorreapi.negocio.controller;

import br.com.ifba.mapadocorreapi.negocio.dto.NegocioGetResponseDto;
import br.com.ifba.mapadocorreapi.negocio.dto.NegocioPostRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface NegocioIController {

    ResponseEntity<Page<NegocioGetResponseDto>> findAll(Pageable pageable);

    ResponseEntity<?> findById(Long id);

    ResponseEntity<?> update(Long id, NegocioPostRequestDto negocioPostRequestDto);

    ResponseEntity<?> delete(Long id);
}