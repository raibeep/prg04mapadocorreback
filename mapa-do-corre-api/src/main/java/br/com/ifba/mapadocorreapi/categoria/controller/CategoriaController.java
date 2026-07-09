package br.com.ifba.mapadocorreapi.categoria.controller;

import br.com.ifba.mapadocorreapi.categoria.dto.CategoriaGetResponseDto;
import br.com.ifba.mapadocorreapi.categoria.dto.CategoriaPostRequestDto;
import br.com.ifba.mapadocorreapi.categoria.entity.Categoria;
import br.com.ifba.mapadocorreapi.categoria.service.CategoriaIService;
import br.com.ifba.mapadocorreapi.infrastructure.mapper.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController implements CategoriaIController {

    private final CategoriaIService categoriaService;
    private final ObjectMapperUtil objectMapperUtil;

    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> save(@RequestBody @Valid CategoriaPostRequestDto categoriaPostRequestDto) {

        //@RequestBody pega o JSON enviado pelo cliente
        //e transforma em objeto Java
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.map(categoriaService.save((objectMapperUtil.map(categoriaPostRequestDto, Categoria.class))),
                        CategoriaGetResponseDto.class));
    }

    @GetMapping(path = "/findall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<CategoriaGetResponseDto>> findAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.findAll(pageable)
                        .map(c -> objectMapperUtil.map(c, CategoriaGetResponseDto.class)));
    }

    @GetMapping("/findbyid/{id}") //irá buscar pelo id
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(objectMapperUtil.map(categoriaService.findById(id), CategoriaGetResponseDto.class));
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid CategoriaPostRequestDto categoriaPostRequestDto) {
        categoriaService.update(id, objectMapperUtil.map(categoriaPostRequestDto, Categoria.class));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    //o nome já é bem sugestivo,
    //ele é responsável por deletar dados
    public ResponseEntity<?> delete(@PathVariable Long id) {
        categoriaService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}