package br.com.ifba.mapadocorreapi.cliente.repository;

import br.com.ifba.mapadocorreapi.cliente.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c FROM Cliente c WHERE c.usuario.id = :usuarioId")
    Optional<Cliente> findByUsuarioId(@Param("usuarioId") Long usuarioId);
}