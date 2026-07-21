package br.com.ifba.mapadocorreapi.endereco.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoGetResponseDto {

    private Long id;
    private String cep;
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private Long negocioId;
    private String nomeNegocio;
    private Long clienteId;
}