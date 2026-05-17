package br.com.ifba.mapadocorreapi.entity;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String texto;
    private Date criadoEm;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "postagem_id")
    private Postagem postagem;
}