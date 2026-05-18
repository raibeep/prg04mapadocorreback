package br.com.ifba.mapadocorreapi.controller;

import br.com.ifba.mapadocorreapi.entity.Categoria;
import br.com.ifba.mapadocorreapi.service.CategoriaIService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
public class CategoriaController implements CategoriaIController{
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
    @PostMapping
    public Categoria save(@RequestBody Categoria categoria){//@RequestBody pega o JSON enviado pelo cliente e transforma em objeto java
        return categoriaService.save(categoria);
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
    @GetMapping
    public List<Categoria> listCategorias() {
        return categoriaService.listCategorias();
    }

    @GetMapping("/{id}")//irá buscar pelo id
    public Categoria findById(@PathVariable String id){
        return categoriaService.findById(id);
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
    @PutMapping("/{id}")
    public Categoria updateCategoria(@PathVariable String id, @RequestBody Categoria categoria) {
        return categoriaService.updateCategoria(id,categoria);
    }

    @DeleteMapping("/{id}")//o nome já é bem sugestivo, ele é responsável por deletar dados
    public void deleteCategoria(@PathVariable String id){
        categoriaService.deleteCategoria(id);
    }
}
