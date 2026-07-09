package br.com.ifba.mapadocorreapi.itempedido.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoPostRequestDto {

    @NotNull(message = "Pedido obrigatório.")
    private Long pedidoId;

    @NotNull(message = "Produto obrigatório.")
    private Long produtoId;

    @NotNull(message = "Quantidade obrigatória.")
    @Min(value = 1, message = "Quantidade deve ser maior que zero.")
    private Integer quantidade;
}