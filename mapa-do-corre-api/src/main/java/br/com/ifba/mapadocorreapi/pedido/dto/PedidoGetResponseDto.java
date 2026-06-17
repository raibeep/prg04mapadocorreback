package br.com.ifba.mapadocorreapi.pedido.dto;

import br.com.ifba.mapadocorreapi.enums.StatusPedido;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoGetResponseDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("valorTotal")
    private BigDecimal valorTotal;

    @JsonProperty("status")
    private StatusPedido status;

    @JsonProperty("criadoEm")
    private Date criadoEm;

    @JsonProperty("clienteEmail")
    private String clienteEmail;

    @JsonProperty("negocioNome")
    private String negocioNome;
}