package br.com.ifba.mapadocorreapi.avaliacao.service;

import br.com.ifba.mapadocorreapi.avaliacao.dto.AvaliacaoGetResponseDto;
import br.com.ifba.mapadocorreapi.avaliacao.entity.Avaliacao;
import br.com.ifba.mapadocorreapi.avaliacao.repository.AvaliacaoRepository;
import br.com.ifba.mapadocorreapi.cliente.repository.ClienteRepository;
import br.com.ifba.mapadocorreapi.infrastructure.exception.BusinessException;
import br.com.ifba.mapadocorreapi.infrastructure.mapper.ObjectMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AvaliacaoService implements AvaliacaoIService{

    private final AvaliacaoRepository avaliacaoRepository;
    private final ClienteRepository clienteRepository;
    private final ObjectMapperUtil objectMapperUtil;
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

    @Override
    @Transactional(readOnly = true)
    public Page<AvaliacaoGetResponseDto> findByNegocioId(Long negocioId, Pageable pageable) {
        Page<Avaliacao> avaliacoes = avaliacaoRepository.findByNegocioId(negocioId, pageable);
        return avaliacoes.map(avaliacao -> {
            // Converte os dados padrão usando o ModelMapper
            AvaliacaoGetResponseDto dto = objectMapperUtil.map(avaliacao, AvaliacaoGetResponseDto.class);

            // Se existir um usuário autor associado à avaliação
            if (avaliacao.getAutor() != null) {
                // Busca o cliente pelo ID do Usuário
                clienteRepository.findByUsuarioId(avaliacao.getAutor().getId())
                        .ifPresentOrElse(
                                cliente -> dto.setAutorNome(cliente.getNome()),
                                () -> dto.setAutorNome(avaliacao.getAutor().getEmail())
                        );
            }

            return dto;
        });
    }
}
