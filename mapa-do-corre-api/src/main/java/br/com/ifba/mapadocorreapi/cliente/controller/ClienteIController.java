package br.com.ifba.mapadocorreapi.cliente.controller;

import br.com.ifba.mapadocorreapi.cliente.dto.ClienteGetResponseDto;
import br.com.ifba.mapadocorreapi.cliente.dto.ClientePostRequestDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface ClienteIController {
    public ResponseEntity<?> save(@RequestBody @Valid ClientePostRequestDto clientePostRequestDto);

    public ResponseEntity<Page<ClienteGetResponseDto>> findAll(Pageable pageable);

    public ResponseEntity<?> findById(@PathVariable Long id);

    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid ClientePostRequestDto clientePostRequestDto);

    public ResponseEntity<?> updateSenha(@PathVariable Long id, @RequestParam String senhaAtual, @RequestParam String novaSenha);

    public ResponseEntity<?> delete(@PathVariable Long id);
}
