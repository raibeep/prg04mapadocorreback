package br.com.ifba.mapadocorreapi.negocio.controller;

import br.com.ifba.mapadocorreapi.categoria.entity.Categoria;
import br.com.ifba.mapadocorreapi.categoria.service.CategoriaIService;
import br.com.ifba.mapadocorreapi.infrastructure.mapper.ObjectMapperUtil;
import br.com.ifba.mapadocorreapi.negocio.dto.NegocioGetResponseDto;
import br.com.ifba.mapadocorreapi.negocio.dto.NegocioPostRequestDto;
import br.com.ifba.mapadocorreapi.negocio.entity.Negocio;
import br.com.ifba.mapadocorreapi.negocio.service.NegocioIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/negocios")
@RequiredArgsConstructor
public class NegocioController implements NegocioIController {

    private final NegocioIService negocioService;
    private final CategoriaIService categoriaService;
    private final ObjectMapperUtil objectMapperUtil;

    @GetMapping(path = "/findall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<NegocioGetResponseDto>> findAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(negocioService.findAll(pageable)
                        .map(this::toResponseDto));
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(toResponseDto(negocioService.findById(id)));
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid NegocioPostRequestDto negocioPostRequestDto) {

        Negocio negocio = objectMapperUtil.map(negocioPostRequestDto, Negocio.class);

        Categoria categoria = categoriaService.findById(negocioPostRequestDto.getCategoriaId());
        negocio.setCategoria(categoria);

        negocioService.update(id, negocio);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        negocioService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private NegocioGetResponseDto toResponseDto(Negocio negocio) {
        NegocioGetResponseDto dto = objectMapperUtil.map(negocio, NegocioGetResponseDto.class);

        dto.setCategoriaNome(
                negocio.getCategoria() != null
                        ? negocio.getCategoria().getNome()
                        : null
        );

        dto.setCategoriaId(
                negocio.getCategoria() != null
                        ? negocio.getCategoria().getId()
                        : null
        );

        dto.setDonoEmail(
                negocio.getDono() != null
                        ? negocio.getDono().getEmail()
                        : null
        );

        return dto;
    }
}