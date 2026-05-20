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
public class CategoriaService implements CategoriaIService {

    private final CategoriaRepository categoriaRepository;

    private static final Logger log =
            LoggerFactory.getLogger(CategoriaService.class);

    @Override
    public Categoria save(Categoria categoria) throws RuntimeException {

        if (categoria == null) {
            throw new RuntimeException(
                    "Dados de Categoria não preenchidos"
            );

        } else if (categoriaRepository.findByNome(categoria.getNome()).isPresent()) {

            throw new RuntimeException(
                    "Categoria já cadastrada"
            );
        }else {

            log.info("Salvando o objeto Categoria");

            return categoriaRepository.save(categoria);
        }
    }

    @Override
    public List<Categoria> findAll() {

        log.info("Listando categorias");

        return categoriaRepository.findAll();
    }

    @Override
    public Categoria findById(Long id) {

        log.info("Buscando categoria por ID");
        return categoriaRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Categoria não encontrada"));
    }

    @Override
    public Categoria updateCategoria(Long id,
                                     Categoria categoria)
            throws RuntimeException {

        Categoria categoriaExistente = findById(id);

        categoriaExistente.setNome(categoria.getNome());
        categoriaExistente.setIcone(categoria.getIcone());

        log.info("Atualizando categoria");

        return categoriaRepository.save(categoriaExistente);
    }

    @Override
    public void deleteCategoria(Long id)
            throws RuntimeException {

        Categoria categoria = findById(id);

        log.info("Deletando categoria");

        categoriaRepository.delete(categoria);
    }
}