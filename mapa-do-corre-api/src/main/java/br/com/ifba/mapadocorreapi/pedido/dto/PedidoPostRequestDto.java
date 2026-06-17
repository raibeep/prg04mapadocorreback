package br.com.ifba.mapadocorreapi.pedido.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoPostRequestDto {

    @JsonProperty("valorTotal")
    @NotNull(message = "O valor total é obrigatório")
    @Positive(message = "O valor total deve ser positivo")
    private BigDecimal valorTotal;
}