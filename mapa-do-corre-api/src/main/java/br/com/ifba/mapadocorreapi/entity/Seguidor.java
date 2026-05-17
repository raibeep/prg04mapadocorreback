package br.com.ifba.mapadocorreapi.entity;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Date seguidoEm;

    @ManyToOne
    @JoinColumn(name = "seguidorId")
    private Usuario seguidor;

    @ManyToOne
    @JoinColumn(name = "seguindoId")
    private Usuario seguindo;
}