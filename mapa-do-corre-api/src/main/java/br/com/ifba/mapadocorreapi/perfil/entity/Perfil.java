package br.com.ifba.mapadocorreapi.perfil.entity;

import br.com.ifba.mapadocorreapi.enums.TiposPerfil;
import br.com.ifba.mapadocorreapi.infrastructure.persistence.entity.PersistenceEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Perfil extends PersistenceEntity {
    private TiposPerfil nivelAcesso;
}
