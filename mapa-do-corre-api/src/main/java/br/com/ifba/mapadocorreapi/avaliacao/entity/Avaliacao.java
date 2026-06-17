package br.com.ifba.mapadocorreapi.avaliacao.entity;

import br.com.ifba.mapadocorreapi.infrastructure.persistence.entity.PersistenceEntity;
import br.com.ifba.mapadocorreapi.negocio.entity.Negocio;
import br.com.ifba.mapadocorreapi.usuario.entity.Usuario;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Avaliacao extends PersistenceEntity {

    private Integer nota;
    private Date criadoEm;
    private String comentario;
    private String resposta;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "negocio_id")
    private Negocio negocio;
}