package br.com.ifba.mapadocorreapi.usuario.service;

import br.com.ifba.mapadocorreapi.infrastructure.exception.BusinessException;
import br.com.ifba.mapadocorreapi.usuario.entity.Usuario;
import br.com.ifba.mapadocorreapi.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UsuarioIService{
    private final UsuarioRepository usuarioRepository;

    @Override
    public Page<Usuario> findAll(Pageable pageble){
        return usuarioRepository.findAll(pageble);
    }

    @Override
    public Usuario findById(Long id){
        return usuarioRepository.findById(id).orElseThrow(() ->
            new BusinessException("Usuário não encontrado."));
    }

    @Override
    @Transactional
    public Usuario update(Long id, Usuario usuario){
        Usuario usuarioExistente = findById(id);

        if (usuarioRepository.existsByEmailAndIdNot(usuario.getEmail(), id)) {
            throw new BusinessException("Email já está em uso.");
        }

        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setSenha(usuario.getSenha());

        return usuarioRepository.save(usuarioExistente);
    }

    @Override
    public boolean emailExiste(String email){
        return usuarioRepository.existsByEmail(email);
    }
}
