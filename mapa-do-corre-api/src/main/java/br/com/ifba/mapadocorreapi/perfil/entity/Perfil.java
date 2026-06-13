package br.com.ifba.mapadocorreapi.perfil.entity;

import br.com.ifba.mapadocorreapi.enums.TiposPerfil;
import br.com.ifba.mapadocorreapi.infrastructure.persistence.entity.PersistenceEntity;
import jakarta.persistence.Entity;

@Entity
public class Perfil extends PersistenceEntity {
    private TiposPerfil nivelAcesso;
}
