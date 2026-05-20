package br.com.ifba.mapadocorreapi.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Postagem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;

    private String conteudo;
    private String imagem;
    private Date criadoEm;
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "negocio_id")
    private Negocio negocio;
}