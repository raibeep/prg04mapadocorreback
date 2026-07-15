package br.com.ifba.mapadocorreapi.negocio.service;

import br.com.ifba.mapadocorreapi.infrastructure.exception.BusinessException;
import br.com.ifba.mapadocorreapi.negocio.entity.Negocio;
import br.com.ifba.mapadocorreapi.negocio.repository.NegocioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NegocioService implements NegocioIService{
    private final NegocioRepository negocioRepository;

    @Override
    @Transactional
    public Negocio save(Negocio negocio){
        if(negocioRepository.findByNome(negocio.getNome()).isPresent()){
            throw new BusinessException("Nome de negócio existente.");
        }
        if (negocio.getContato().equals(negocio.getDono().getTelefone())) {
            throw new BusinessException(
                    "O telefone do negócio deve ser diferente do telefone do empresário."
            );
        }

            return negocioRepository.save(negocio);
    }

    @Override
    public Page<Negocio> findAll(Pageable pageable) {
        return negocioRepository.findAll(pageable);
    }

    @Override
    public Negocio findById(Long id) {
        return negocioRepository.findById(id).orElseThrow(() ->
                new BusinessException("Negócio não encontrado."));
    }

    @Override
    @Transactional
    public Negocio update(Long id, Negocio negocio) {
        Negocio negocioExistente = findById(id);

        if (negocio.getContato().equals(negocio.getDono().getTelefone())) {
            throw new BusinessException(
                    "O telefone do negócio deve ser diferente do telefone do empresário."
            );
        }

        negocioExistente.setNome(negocio.getNome());
        negocioExistente.setContato(negocio.getContato());
        negocioExistente.setDescricao(negocio.getDescricao());
        negocioExistente.setFoto(negocio.getFoto());
        negocioExistente.setCategoria(negocio.getCategoria());

        return negocioRepository.save(negocioExistente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Negocio negocio = findById(id);

        negocioRepository.delete(negocio);
    }
}
