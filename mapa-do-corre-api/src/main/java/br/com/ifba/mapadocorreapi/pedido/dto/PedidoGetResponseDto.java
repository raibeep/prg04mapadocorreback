package br.com.ifba.mapadocorreapi.pedido.dto;

import br.com.ifba.mapadocorreapi.enums.StatusPedido;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class PedidoGetResponseDto {

    private Long id;
    private BigDecimal valorTotal;
    private StatusPedido status;
    private Date criadoEm;
    private Long clienteId;
    private String clienteNome;
    private Long enderecoId;
    private String cidadeEntrega;
}