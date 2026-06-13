package br.com.ifba.mapadocorreapi.categoria.entity;

import br.com.ifba.mapadocorreapi.infrastructure.persistence.entity.PersistenceEntity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Categoria extends PersistenceEntity {
    private String nome;
    private String icone;
}