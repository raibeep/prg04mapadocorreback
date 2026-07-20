package br.com.ifba.mapadocorreapi.pedido.dto;

import br.com.ifba.mapadocorreapi.enums.MetodoPagamento;
import br.com.ifba.mapadocorreapi.itempedido.dto.ItemPedidoPostRequestDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PedidoPostRequestDto {

    @NotNull(message = "Endereço obrigatório.")
    private Long enderecoId;

    @NotNull(message = "Método de pagamento obrigatório.")
    private MetodoPagamento metodoPagamento;

    @NotEmpty(message = "O pedido deve possuir pelo menos um item.")
    private List<ItemPedidoPostRequestDto> itens;
}