package br.com.ifba.mapadocorreapi.endereco.entity;
import br.com.ifba.mapadocorreapi.cliente.entity.Cliente;
import br.com.ifba.mapadocorreapi.infrastructure.persistence.entity.PersistenceEntity;
import br.com.ifba.mapadocorreapi.negocio.entity.Negocio;
import br.com.ifba.mapadocorreapi.pedido.entity.Pedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco extends PersistenceEntity {

    private String cep;
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "negocio_id")
    private Negocio negocio;

    @OneToMany(mappedBy = "endereco")
    private List<Pedido> pedidos = new ArrayList<>();
}