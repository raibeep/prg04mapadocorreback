package br.com.ifba.mapadocorreapi.usuario.repository;

import br.com.ifba.mapadocorreapi.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmailAndIdNot(String email, Long id);
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
}
