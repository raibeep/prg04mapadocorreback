package br.com.ifba.mapadocorreapi.seguidor.entity;
import br.com.ifba.mapadocorreapi.usuario.entity.Usuario;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Seguidor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date seguidoEm;

    @ManyToOne
    @JoinColumn(name = "seguidorId")
    private Usuario seguidor;

    @ManyToOne
    @JoinColumn(name = "seguindoId")
    private Usuario seguindo;
}