package br.com.ifba.mapadocorreapi.empresario.service;

import br.com.ifba.mapadocorreapi.avaliacao.entity.Avaliacao;
import br.com.ifba.mapadocorreapi.avaliacao.repository.AvaliacaoRepository;
import br.com.ifba.mapadocorreapi.empresario.entity.Empresario;
import br.com.ifba.mapadocorreapi.empresario.repository.EmpresarioRepository;
import br.com.ifba.mapadocorreapi.enums.TiposPerfil;
import br.com.ifba.mapadocorreapi.infrastructure.exception.BusinessException;
import br.com.ifba.mapadocorreapi.negocio.entity.Negocio;
import br.com.ifba.mapadocorreapi.negocio.repository.NegocioRepository;
import br.com.ifba.mapadocorreapi.perfil.entity.Perfil;
import br.com.ifba.mapadocorreapi.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class EmpresarioService implements EmpresarioIService{
    private final EmpresarioRepository empresarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final NegocioRepository negocioRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Empresario save(Empresario empresario) {
        if(empresario.getUsuario() == null){
            throw new BusinessException("Dados do usuário não informados.");
        }

        if(usuarioRepository.existsByEmail(empresario.getUsuario().getEmail())){
            throw new BusinessException("Email já cadastrado.");
        }

        empresario.getUsuario().setSenha(passwordEncoder.encode(empresario.getUsuario().getSenha()));

        Perfil perfil = new Perfil();
        perfil.setNivelAcesso(TiposPerfil.EMPRESARIO);

        empresario.getUsuario().setPerfil(perfil);

        return empresarioRepository.save(empresario);
    }

    @Override
    public Page<Empresario> findAll(Pageable pageable) {
        return empresarioRepository.findAll(pageable);
    }

    @Override
    public Empresario findById(Long id) {
        return empresarioRepository.findById(id).orElseThrow(() ->
                new BusinessException("Empresário não encontrado!"));
    }

    @Override
    @Transactional
    public Empresario update(Long id, Empresario empresario) {
        Empresario empresarioExistente = findById(id);

        if(usuarioRepository.existsByEmailAndIdNot(
                empresario.getUsuario().getEmail(),
                empresarioExistente.getUsuario().getId())){
            throw new BusinessException("Email já está em uso.");
        }

        empresarioExistente.setNome(empresario.getNome());
        empresarioExistente.setBio(empresario.getBio());
        empresarioExistente.setCpf(empresario.getCpf());
        empresarioExistente.setTelefone(empresario.getTelefone());
        empresarioExistente.setFotoPerfil(empresario.getFotoPerfil());
        empresarioExistente.getUsuario().setEmail(empresario.getUsuario().getEmail());

        return empresarioRepository.save(empresarioExistente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Empresario empresario = findById(id);
        empresarioRepository.delete(empresario);
    }

    @Override
    @Transactional
    public void updateSenha(Long id, String senhaAtual, String novaSenha) {
        Empresario empresario = findById(id);

        // Compara com matches porque a senha no banco está criptografada
        if (!passwordEncoder.matches(senhaAtual, empresario.getUsuario().getSenha())) {
            throw new BusinessException("Senha atual incorreta.");
        }

        empresario.getUsuario().setSenha(passwordEncoder.encode(novaSenha));
        empresarioRepository.save(empresario);
    }

    @Override
    @Transactional
    public Negocio cadastrarNegocio(Long empresarioId, Negocio negocio) {
        Empresario empresario = findById(empresarioId);

        negocio.setDono(empresario.getUsuario());
        negocio.setCriadoEm(new Date());

        return negocioRepository.save(negocio);
    }

    @Override
    @Transactional
    public Avaliacao responderAvaliacao(Long empresarioId, Long avaliacaoId, String resposta) {
        findById(empresarioId); // garante que o empresário existe

        Avaliacao avaliacao = avaliacaoRepository.findById(avaliacaoId)
                .orElseThrow(() -> new BusinessException("Avaliação não encontrada."));

        avaliacao.setResposta(resposta);

        return avaliacaoRepository.save(avaliacao);
    }

    @Override
    public Negocio getNegocioDoEmpresario(Long empresarioId) {
        Empresario empresario = findById(empresarioId);
        return negocioRepository.findByDonoId(empresario.getUsuario().getId()).orElse(null);
    }
}
