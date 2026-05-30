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

/*
 * @RestController
 * Diz ao Spring que essa classe é um controller REST.
 *
 * Ou seja:
 * ela vai receber requisições HTTP
 * e devolver respostas em JSON.
 */
@RestController

/*
 * @RequestMapping("/categorias")
 *
 * Define a rota base da API.
 *
 * Tudo dentro dessa classe começará com:
 * /categorias
 *
 * Exemplos:
 * GET    /categorias
 * POST   /categorias
 * GET    /categorias/1
 */
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController implements CategoriaIController {

    private final CategoriaIService categoriaService;
    private final ObjectMapperUtil objectMapperUtil;

    /*
     * @PostMapping
     *
     * Endpoint responsável por SALVAR dados.
     *
     * Responde ao método HTTP POST.
     *
     * URL:
     * POST /categorias
     */
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

    /*
     * @GetMapping
     *
     * Endpoint responsável por LISTAR dados.
     *
     * Responde ao método HTTP GET.
     *
     * URL:
     * GET /categorias
     */
    @GetMapping(path = "/findall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(objectMapperUtil.mapAll(
                        categoriaService.findAll(),
                        CategoriaGetResponseDto.class));
    }

    @GetMapping("/findbyid/{id}") //irá buscar pelo id
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(objectMapperUtil.map(
                        categoriaService.findById(id),
                        CategoriaGetResponseDto.class));
    }

    /*
     * @PutMapping("/{id}")
     *
     * Endpoint responsável por ATUALIZAR dados.
     *
     * Usa o método HTTP PUT.
     *
     * Exemplo:
     * PUT /categorias/10
     */
    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
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