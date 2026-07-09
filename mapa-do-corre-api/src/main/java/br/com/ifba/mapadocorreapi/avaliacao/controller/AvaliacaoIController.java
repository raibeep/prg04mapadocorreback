package br.com.ifba.mapadocorreapi.avaliacao.controller;

import br.com.ifba.mapadocorreapi.avaliacao.dto.AvaliacaoGetResponseDto;
import br.com.ifba.mapadocorreapi.avaliacao.dto.AvaliacaoPostRequestDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface AvaliacaoIController {
    public ResponseEntity<?> save (@RequestBody @Valid AvaliacaoPostRequestDto avaliacaoPostRequestDto);

    public ResponseEntity<Page<AvaliacaoGetResponseDto>> findAll(Pageable pageable);

    public ResponseEntity<?> findById(@PathVariable Long id);

    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid AvaliacaoPostRequestDto avaliacaoPostRequestDto);

    public ResponseEntity<?> delete(@PathVariable Long id);
}
