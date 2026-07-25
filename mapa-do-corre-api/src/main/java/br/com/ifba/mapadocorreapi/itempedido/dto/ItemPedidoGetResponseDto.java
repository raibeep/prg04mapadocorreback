package br.com.ifba.mapadocorreapi.itempedido.dto;

import br.com.ifba.mapadocorreapi.enums.StatusItemPedido;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ItemPedidoGetResponseDto {

    private Long id;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private StatusItemPedido status;
    private Long produtoId;
    private String nomeProduto;
    private String fotoProduto;
    private Long pedidoId;
    private String clienteNome;
    private String enderecoResumo;
    private Date criadoEm;
    private Long negocioId;
    private String negocioNome;
}