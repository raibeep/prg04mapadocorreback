package br.com.ifba.mapadocorreapi.pedido.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoPostRequestDto {
    @NotNull(message = "Endereço obrigatório.")
    private Long enderecoId;
}