package br.com.ifba.mapadocorreapi.negocio.entity;
import br.com.ifba.mapadocorreapi.categoria.entity.Categoria;
import br.com.ifba.mapadocorreapi.enums.TipoNegocio;
import br.com.ifba.mapadocorreapi.infrastructure.persistence.entity.PersistenceEntity;
import br.com.ifba.mapadocorreapi.usuario.entity.Usuario;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Negocio extends PersistenceEntity {
    private String nome;
    private String descricao;
    private String contato;
    private String foto;
    private Date criadoEm;

    @Enumerated(EnumType.STRING)
    private TipoNegocio tipo;

    @ManyToOne
    @JoinColumn(name = "dono_id")
    private Usuario dono;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}