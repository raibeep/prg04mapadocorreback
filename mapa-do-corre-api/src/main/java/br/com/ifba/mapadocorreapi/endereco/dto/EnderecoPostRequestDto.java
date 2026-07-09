package br.com.ifba.mapadocorreapi.endereco.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoPostRequestDto {

    @NotBlank(message = "A rua é obrigatória.")
    private String rua;

    @NotBlank(message = "O bairro é obrigatório.")
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória.")
    private String cidade;

    @NotBlank(message = "O estado é obrigatório.")
    private String estado;

    private Long negocioId;
}