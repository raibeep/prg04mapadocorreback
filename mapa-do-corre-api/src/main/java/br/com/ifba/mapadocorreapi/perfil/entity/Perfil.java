package br.com.ifba.mapadocorreapi.perfil.entity;

import br.com.ifba.mapadocorreapi.enums.TiposPerfil;
import br.com.ifba.mapadocorreapi.infrastructure.persistence.entity.PersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Perfil extends PersistenceEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private TiposPerfil nivelAcesso;
}
