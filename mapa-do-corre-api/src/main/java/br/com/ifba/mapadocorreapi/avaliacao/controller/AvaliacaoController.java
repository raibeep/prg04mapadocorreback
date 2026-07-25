package br.com.ifba.mapadocorreapi.avaliacao.controller;

import br.com.ifba.mapadocorreapi.avaliacao.dto.AvaliacaoGetResponseDto;
import br.com.ifba.mapadocorreapi.avaliacao.dto.AvaliacaoPostRequestDto;
import br.com.ifba.mapadocorreapi.avaliacao.entity.Avaliacao;
import br.com.ifba.mapadocorreapi.avaliacao.service.AvaliacaoIService;
import br.com.ifba.mapadocorreapi.infrastructure.mapper.ObjectMapperUtil;
import br.com.ifba.mapadocorreapi.negocio.entity.Negocio;
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
public class AvaliacaoController implements AvaliacaoIController {
    private final ObjectMapperUtil objectMapperUtil;
    private final AvaliacaoIService avaliacaoService;

    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody @Valid AvaliacaoPostRequestDto dto) {

        Avaliacao avaliacao = objectMapperUtil.map(dto, Avaliacao.class);

        Negocio negocio = new Negocio();
        negocio.setId(dto.getNegocioId());

        avaliacao.setNegocio(negocio);

        Avaliacao salva = avaliacaoService.save(avaliacao);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.map(salva, AvaliacaoGetResponseDto.class));
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

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid AvaliacaoPostRequestDto dto) {

        Avaliacao avaliacao = objectMapperUtil.map(dto, Avaliacao.class);

        Negocio negocio = new Negocio();
        negocio.setId(dto.getNegocioId());

        avaliacao.setNegocio(negocio);

        avaliacaoService.update(id, avaliacao);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        avaliacaoService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/negocio/{negocioId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<AvaliacaoGetResponseDto>> findByNegocio(@PathVariable Long negocioId, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(
                avaliacaoService.findByNegocioId(negocioId, pageable)
                        .map(a -> objectMapperUtil.map(a, AvaliacaoGetResponseDto.class))
        );
    }
}
