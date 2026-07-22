package br.com.ifba.mapadocorreapi.negocio.entity;
import br.com.ifba.mapadocorreapi.categoria.entity.Categoria;
import br.com.ifba.mapadocorreapi.empresario.entity.Empresario;
import br.com.ifba.mapadocorreapi.endereco.entity.Endereco;
import br.com.ifba.mapadocorreapi.infrastructure.persistence.entity.PersistenceEntity;
import br.com.ifba.mapadocorreapi.produto.entity.Produto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Negocio extends PersistenceEntity {
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    private String contato;
    private String foto;
    private Date criadoEm;

    @OneToOne
    @JoinColumn(name = "dono_id", referencedColumnName = "id")
    private Empresario dono;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @OneToMany(mappedBy = "negocio")
    private List<Produto> produtos = new ArrayList<>();

    @OneToMany(mappedBy = "negocio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos = new ArrayList<>();
}