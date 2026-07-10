package br.com.ifba.mapadocorreapi.produto.controller;

import br.com.ifba.mapadocorreapi.infrastructure.mapper.ObjectMapperUtil;
import br.com.ifba.mapadocorreapi.negocio.service.NegocioIService;
import br.com.ifba.mapadocorreapi.produto.dto.ProdutoGetResponseDto;
import br.com.ifba.mapadocorreapi.produto.dto.ProdutoPostRequestDto;
import br.com.ifba.mapadocorreapi.produto.entity.Produto;
import br.com.ifba.mapadocorreapi.produto.service.ProdutoIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController implements ProdutoIController {

    private final ProdutoIService produtoService;
    private final NegocioIService negocioService;
    private final ObjectMapperUtil objectMapperUtil;

    @PostMapping(
            path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> save(@RequestBody @Valid ProdutoPostRequestDto dto) {

        Produto produto = objectMapperUtil.map(dto, Produto.class);

        produto.setNegocio(
                negocioService.findById(dto.getNegocioId())
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        objectMapperUtil.map(
                                produtoService.save(produto),
                                ProdutoGetResponseDto.class
                        )
                );
    }

    @Override
    @GetMapping(path = "/findall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ProdutoGetResponseDto>> findAll(Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        produtoService.findAll(pageable)
                                .map(p -> objectMapperUtil.map(p, ProdutoGetResponseDto.class))
                );
    }

    @Override
    @GetMapping("/findbyid/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        objectMapperUtil.map(
                                produtoService.findById(id),
                                ProdutoGetResponseDto.class
                        )
                );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody @Valid ProdutoPostRequestDto dto) {

        Produto produto = objectMapperUtil.map(dto, Produto.class);

        produto.setNegocio(
                negocioService.findById(dto.getNegocioId())
        );

        produtoService.update(id, produto);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    @DeleteMapping(
            value = "/delete/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> delete(@PathVariable Long id) {

        produtoService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/negocio/{negocioId}")
    public ResponseEntity<List<ProdutoGetResponseDto>> findByNegocio(@PathVariable Long negocioId) {

        List<ProdutoGetResponseDto> response = produtoService.findByNegocioId(negocioId).stream()
                .map(produto -> objectMapperUtil.map(produto, ProdutoGetResponseDto.class)).toList();

        return ResponseEntity.ok(response);
    }
}