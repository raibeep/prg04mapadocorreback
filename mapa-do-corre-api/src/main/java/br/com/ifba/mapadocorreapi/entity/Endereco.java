package br.com.ifba.mapadocorreapi.entity;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String rua;
    private String bairro;
    private String cidade;
    private String estado;

    @OneToOne
    @JoinColumn(name="negocioId")
    private Negocio negocio;
}