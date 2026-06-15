package br.com.ifba.mapadocorreapi.usuario.service;

import br.com.ifba.mapadocorreapi.usuario.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioIService {
    public Page<Usuario> findAll(Pageable pageble);
    public Usuario findById(Long id);
    public Usuario update(Long id, Usuario usuario);
}
