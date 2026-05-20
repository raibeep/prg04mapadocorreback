package br.com.ifba.mapadocorreapi.entity;
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
    @GeneratedValue(strategy = GenerationType.UUID)
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