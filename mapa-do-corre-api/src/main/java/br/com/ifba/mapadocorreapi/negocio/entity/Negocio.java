package br.com.ifba.mapadocorreapi.negocio.entity;
import br.com.ifba.mapadocorreapi.categoria.entity.Categoria;
import br.com.ifba.mapadocorreapi.usuario.entity.Usuario;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Negocio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private String contato;
    private String foto;
    private Date criadoEm;

    @ManyToOne
    @JoinColumn(name = "dono_id")
    private Usuario dono;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}