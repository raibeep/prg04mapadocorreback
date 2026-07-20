package br.com.ifba.mapadocorreapi.pedido.dto;

import br.com.ifba.mapadocorreapi.enums.MetodoPagamento;
import br.com.ifba.mapadocorreapi.enums.StatusPagamento;
import br.com.ifba.mapadocorreapi.enums.StatusPedido;
import br.com.ifba.mapadocorreapi.itempedido.dto.ItemPedidoGetResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
    private StatusPagamento statusPagamento;
    private MetodoPagamento metodoPagamento;
    private List<ItemPedidoGetResponseDto> itens;
}