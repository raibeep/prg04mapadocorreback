package br.com.ifba.mapadocorreapi.usuario.controller;

import br.com.ifba.mapadocorreapi.usuario.dto.EmailRequestDto;
import br.com.ifba.mapadocorreapi.usuario.dto.UsuarioGetResponseDto;
import br.com.ifba.mapadocorreapi.usuario.dto.UsuarioPostRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface UsuarioIController {
    ResponseEntity<Page<UsuarioGetResponseDto>> findAll(Pageable pageable);
    ResponseEntity<?> findById(Long id);
    ResponseEntity<?> update(Long id, UsuarioPostRequestDto usuarioPostRequestDto);
    public ResponseEntity<Boolean> verificarEmail(
            @RequestBody EmailRequestDto dto);
}
