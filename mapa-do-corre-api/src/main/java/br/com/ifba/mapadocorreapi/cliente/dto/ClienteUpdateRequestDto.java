package br.com.ifba.mapadocorreapi.cliente.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteUpdateRequestDto {

    @JsonProperty("nome")
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @JsonProperty("telefone")
    @NotBlank(message = "O telefone é obrigatório")
    private String telefone;

    @JsonProperty("fotoPerfil")
    private String fotoPerfil;

    @JsonProperty("bio")
    private String bio;
}