package br.com.ifba.mapadocorreapi.pedido.dto;

import br.com.ifba.mapadocorreapi.enums.MetodoPagamento;
import br.com.ifba.mapadocorreapi.itempedido.dto.ItemPedidoPostRequestDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PedidoPostRequestDto {

    @NotNull(message = "Endereço obrigatório.")
    private Long enderecoId;

    @NotNull(message = "Método de pagamento obrigatório.")
    private MetodoPagamento metodoPagamento;

    @DecimalMin(value = "0.0", message = "O troco não pode ser negativo.")
    private BigDecimal troco;

    @NotEmpty(message = "O pedido deve possuir pelo menos um item.")
    private List<ItemPedidoPostRequestDto> itens;
}