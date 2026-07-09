package br.com.ifba.mapadocorreapi.produto.entity;

import br.com.ifba.mapadocorreapi.enums.TipoProduto;
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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Produto extends PersistenceEntity {
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Date criadoEm;
    private String foto;

    @Enumerated(EnumType.STRING)
    private TipoProduto tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "negocio_id", nullable = false)
    private Negocio negocio;

    @OneToMany(mappedBy = "produto")
    private List<ItemPedido> itens = new ArrayList<>();
}
