package br.com.ifba.mapadocorreapi.endereco.entity;
import br.com.ifba.mapadocorreapi.negocio.entity.Negocio;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Endereco {
    @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rua;
    private String bairro;
    private String cidade;
    private String estado;

    @OneToOne
    @JoinColumn(name="negocioId")
    private Negocio negocio;
}