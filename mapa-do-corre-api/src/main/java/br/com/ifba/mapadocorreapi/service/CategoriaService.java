package br.com.ifba.mapadocorreapi.service;

import br.com.ifba.mapadocorreapi.entity.Categoria;
import br.com.ifba.mapadocorreapi.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService implements CategoriaIService{

    private final CategoriaRepository categoriaRepository;

    private static final Logger log = LoggerFactory.getLogger(CategoriaService.class);

    @Override
    public Categoria save(Categoria categoria) throws RuntimeException{
        if(categoria == null){
            throw new RuntimeException("Dados de " + "Categoria não preenchidos");
        }else if(categoria.getId() != null){
            throw new RuntimeException("Categoria " + "já existente no Banco de Dados");
        } else {
            log.info("Salvando o Objeto Categoria");
            return categoriaRepository.save(categoria);
        }
    }

    @Override
    public List<Categoria> listCategorias() {
        log.info("Listando categorias");

        return categoriaRepository.findAll();
    }

    @Override
    public Categoria findById(String id) {
        log.info("Buscando categoria por ID");

        return categoriaRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Categoria não encontrada"));
    }

    @Override
    public Categoria updateCategoria(String id, Categoria categoria) throws RuntimeException{
        Categoria categoriaExistente = findById(id);

        if (categoriaExistente != null){
            categoriaExistente.setNome(categoria.getNome());
            categoriaExistente.setIcone(categoria.getIcone());

            log.info("Atualizando categoria");

            return categoriaRepository.save(categoria);
        }else {
            throw new RuntimeException("Categoria não encontrada");
        }
    }

    @Override
    public void deleteCategoria(String id) throws RuntimeException{
        Categoria categoria = findById(id);

        log.info("Deletando categoria");
        categoriaRepository.delete(categoria);
    }
}
