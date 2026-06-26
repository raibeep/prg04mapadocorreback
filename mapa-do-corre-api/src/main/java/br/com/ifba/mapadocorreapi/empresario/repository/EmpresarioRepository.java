package br.com.ifba.mapadocorreapi.empresario.repository;

import br.com.ifba.mapadocorreapi.empresario.entity.Empresario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmpresarioRepository extends JpaRepository<Empresario, Long> {
    @Query("SELECT e FROM Empresario e WHERE e.usuario.id = :usuarioId")
    Optional<Empresario> findByUsuarioId(@Param("usuarioId") Long usuarioId);
}
