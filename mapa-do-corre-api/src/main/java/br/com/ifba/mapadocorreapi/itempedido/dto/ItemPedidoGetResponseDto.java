package br.com.ifba.mapadocorreapi.itempedido.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemPedidoGetResponseDto {

    private Long id;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private Long produtoId;
    private String nomeProduto;
    private Long pedidoId;
    private String fotoProduto;
}