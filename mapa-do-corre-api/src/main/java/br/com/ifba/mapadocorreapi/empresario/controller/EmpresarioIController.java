package br.com.ifba.mapadocorreapi.empresario.controller;

import br.com.ifba.mapadocorreapi.avaliacao.dto.AvaliacaoGetResponseDto;
import br.com.ifba.mapadocorreapi.empresario.dto.EmpresarioGetResponseDto;
import br.com.ifba.mapadocorreapi.empresario.dto.EmpresarioPostRequestDto;
import br.com.ifba.mapadocorreapi.negocio.entity.Negocio;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface EmpresarioIController {
    public ResponseEntity<EmpresarioGetResponseDto> save(@RequestBody @Valid EmpresarioPostRequestDto dto);

    public ResponseEntity<Page<EmpresarioGetResponseDto>> findAll(Pageable pageable);

    public ResponseEntity<EmpresarioGetResponseDto> findById(@PathVariable Long id);

    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody @Valid EmpresarioPostRequestDto dto);

    public ResponseEntity<Void> updateSenha(@PathVariable Long id, @RequestParam String senhaAtual, @RequestParam String novaSenha);

    public ResponseEntity<Void> delete(@PathVariable Long id);

    public ResponseEntity<Negocio> cadastrarNegocio(@PathVariable Long empresarioId, @RequestBody @Valid Negocio negocio);

    public ResponseEntity<AvaliacaoGetResponseDto> responderAvaliacao(@PathVariable Long empresarioId, @PathVariable Long avaliacaoId, @RequestParam String resposta);
}
