package br.com.ifba.mapadocorreapi.pedido.entity;

import br.com.ifba.mapadocorreapi.cliente.entity.Cliente;
import br.com.ifba.mapadocorreapi.endereco.entity.Endereco;
import br.com.ifba.mapadocorreapi.enums.StatusPedido;
import br.com.ifba.mapadocorreapi.infrastructure.persistence.entity.PersistenceEntity;
import br.com.ifba.mapadocorreapi.itempedido.entity.ItemPedido;
import br.com.ifba.mapadocorreapi.negocio.entity.Negocio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Pedido extends PersistenceEntity {
    private BigDecimal valorTotal;
    private Date criadoEm;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @OneToMany(mappedBy = "pedido",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<ItemPedido> itens = new ArrayList<>();
}
