package br.com.ifba.mapadocorreapi.comentario.entity;
import br.com.ifba.mapadocorreapi.postagem.entity.Postagem;
import br.com.ifba.mapadocorreapi.usuario.entity.Usuario;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;
    private Date criadoEm;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "postagem_id")
    private Postagem postagem;
}