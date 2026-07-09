package br.com.ifba.mapadocorreapi.avaliacao.controller;

import br.com.ifba.mapadocorreapi.avaliacao.dto.AvaliacaoGetResponseDto;
import br.com.ifba.mapadocorreapi.avaliacao.dto.AvaliacaoPostRequestDto;
import br.com.ifba.mapadocorreapi.avaliacao.entity.Avaliacao;
import br.com.ifba.mapadocorreapi.avaliacao.service.AvaliacaoIService;
import br.com.ifba.mapadocorreapi.infrastructure.mapper.ObjectMapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController implements AvaliacaoIController{
    private final ObjectMapperUtil objectMapperUtil;
    private final AvaliacaoIService avaliacaoService;

    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save (@RequestBody @Valid AvaliacaoPostRequestDto avaliacaoPostRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(objectMapperUtil.map(avaliacaoService.save((objectMapperUtil
                        .map(avaliacaoPostRequestDto, Avaliacao.class))), AvaliacaoGetResponseDto.class));
    }

    @GetMapping(path = "/findall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<AvaliacaoGetResponseDto>> findAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(avaliacaoService.findAll(pageable)
                        .map(c -> objectMapperUtil.map(c, AvaliacaoGetResponseDto.class)));
    }

    @GetMapping("/findbyid/{id}") //irá buscar pelo id
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(objectMapperUtil.map(avaliacaoService.findById(id), AvaliacaoGetResponseDto.class));
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid AvaliacaoPostRequestDto avaliacaoPostRequestDto) {
        avaliacaoService.update(id, objectMapperUtil.map(avaliacaoPostRequestDto, Avaliacao.class));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        avaliacaoService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
