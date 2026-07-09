package br.com.ifba.mapadocorreapi.avaliacao.service;

import br.com.ifba.mapadocorreapi.avaliacao.entity.Avaliacao;
import br.com.ifba.mapadocorreapi.avaliacao.repository.AvaliacaoRepository;
import br.com.ifba.mapadocorreapi.categoria.entity.Categoria;
import br.com.ifba.mapadocorreapi.infrastructure.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AvaliacaoService implements AvaliacaoIService{

    private final AvaliacaoRepository avaliacaoRepository;
    @Override
    public Avaliacao save(Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao);
    }

    @Override
    public Avaliacao findById(Long id) {
        return avaliacaoRepository.findById(id).orElseThrow(() ->
                new BusinessException("Avaliação não encontrada"));
    }

    @Override
    public Page<Avaliacao> findAll(Pageable pageable) {
        return avaliacaoRepository.findAll(pageable);
    }

    @Override
    public Avaliacao update(Long id, Avaliacao avaliacao) {
        Avaliacao avaliacaoExistente = findById(id);

        avaliacaoExistente.setComentario(avaliacao.getComentario());
        avaliacaoExistente.setNota(avaliacao.getNota());

        return avaliacaoRepository.save(avaliacaoExistente);
    }

    @Override
    public void delete(Long id) {
        Avaliacao avaliacao = findById(id);

        avaliacaoRepository.delete(avaliacao);
    }
}
