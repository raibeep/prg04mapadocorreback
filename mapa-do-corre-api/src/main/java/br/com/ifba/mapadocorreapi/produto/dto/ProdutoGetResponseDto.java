package br.com.ifba.mapadocorreapi.produto.dto;

import br.com.ifba.mapadocorreapi.enums.TipoProduto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ProdutoGetResponseDto {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private TipoProduto tipo;
    private String foto;
    private Date criadoEm;
    private Long negocioId;
    private String nomeNegocio;
}