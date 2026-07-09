package br.com.ifba.mapadocorreapi.usuario.repository;

import br.com.ifba.mapadocorreapi.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmailAndIdNot(String email, Long id);
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    @Query("""
            SELECT u
    FROM Usuario u
    JOIN FETCH u.perfil
    WHERE u.email = :email
            """)
    Optional<Usuario> findByEmailWithPerfil(String email);
}
