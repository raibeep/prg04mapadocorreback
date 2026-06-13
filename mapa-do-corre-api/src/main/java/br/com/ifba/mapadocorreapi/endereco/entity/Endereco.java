package br.com.ifba.mapadocorreapi.endereco.entity;
import br.com.ifba.mapadocorreapi.infrastructure.persistence.entity.PersistenceEntity;
import br.com.ifba.mapadocorreapi.negocio.entity.Negocio;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Endereco extends PersistenceEntity {
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;

    @OneToOne
    @JoinColumn(name="negocioId")
    private Negocio negocio;
}