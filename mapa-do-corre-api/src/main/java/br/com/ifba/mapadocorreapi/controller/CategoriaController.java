package br.com.ifba.mapadocorreapi.controller;

import br.com.ifba.mapadocorreapi.entity.Categoria;
import br.com.ifba.mapadocorreapi.service.CategoriaIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> save(@RequestBody Categoria categoria) {

        //@RequestBody pega o JSON enviado pelo cliente
        //e transforma em objeto Java
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoriaService.save(categoria));
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
    @GetMapping(path = "/findall")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.findAll());
    }

    @GetMapping("/{id}") //irá buscar pelo id
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoriaService.findById(id));
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
    public ResponseEntity<?> updateCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        categoriaService.updateCategoria(id, categoria);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE
    )
    //o nome já é bem sugestivo,
    //ele é responsável por deletar dados
    public void deleteCategoria(@PathVariable Long id) {
        categoriaService.deleteCategoria(id);
    }
}