package br.com.ifba.mapadocorreapi.empresario.service;

import br.com.ifba.mapadocorreapi.avaliacao.entity.Avaliacao;
import br.com.ifba.mapadocorreapi.empresario.entity.Empresario;
import br.com.ifba.mapadocorreapi.negocio.entity.Negocio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmpresarioIService {
    public Empresario save(Empresario empresario);

    Page<Empresario> findAll(Pageable pageable);

    Empresario findById(Long id);

    Empresario update(Long id, Empresario empresario);

    void delete(Long id);

    public void updateSenha(Long id, String senhaAtual, String novaSenha);

    public Negocio cadastrarNegocio(Long empresarioId, Negocio negocio);

    public Avaliacao responderAvaliacao(Long empresarioId, Long avaliacaoId, String resposta);
}
