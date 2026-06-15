package br.com.ifba.mapadocorreapi.cliente.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteGetResponseDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("cpf")
    private String cpf;

    @JsonProperty("telefone")
    private String telefone;

    @JsonProperty("fotoPerfil")
    private String fotoPerfil;

    @JsonProperty("bio")
    private String bio;

    @JsonProperty("email")
    private String email;
}
