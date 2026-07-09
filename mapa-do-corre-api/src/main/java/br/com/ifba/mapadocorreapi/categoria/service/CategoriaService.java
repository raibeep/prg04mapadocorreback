package br.com.ifba.mapadocorreapi.categoria.service;

import br.com.ifba.mapadocorreapi.categoria.entity.Categoria;
import br.com.ifba.mapadocorreapi.categoria.repository.CategoriaRepository;
import br.com.ifba.mapadocorreapi.infrastructure.exception.BusinessException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService implements CategoriaIService {

    private final CategoriaRepository categoriaRepository;

    private static final Logger log =
            LoggerFactory.getLogger(CategoriaService.class);

    @Override
    @Transactional
    public Categoria save(Categoria categoria) {

        if (categoriaRepository.findByNome(categoria.getNome()).isPresent()) {

            throw new BusinessException("Categoria já cadastrada");
        } else {

            log.info("Salvando o objeto Categoria");

            return categoriaRepository.save(categoria);
        }
    }

    @Override
    public Page<Categoria> findAll(Pageable pageable) {

        log.info("Listando categorias");

        return categoriaRepository.findAll(pageable);
    }

    @Override
    public Categoria findById(Long id) {

        log.info("Buscando categoria por ID");
        return categoriaRepository.findById(id).orElseThrow(() -> new BusinessException("Categoria não encontrada"));
    }

    @Override
    @Transactional
    public Categoria update(Long id, Categoria categoria) {

        Categoria categoriaExistente = findById(id);

        categoriaExistente.setNome(categoria.getNome());
        categoriaExistente.setIcone(categoria.getIcone());

        log.info("Atualizando categoria");

        return categoriaRepository.save(categoriaExistente);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Categoria categoria = findById(id);

        log.info("Deletando categoria");

        categoriaRepository.delete(categoria);
    }
}