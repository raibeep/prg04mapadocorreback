package br.com.ifba.mapadocorreapi.cliente.dto;

import br.com.ifba.mapadocorreapi.usuario.dto.UsuarioPostRequestDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientePostRequestDto {
    @JsonProperty("nome")
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @JsonProperty("cpf")
    @NotBlank(message = "O CPF é obrigatório")
    @Size(min = 11, max = 11, message = "O CPF deve ter 11 dígitos")
    private String cpf;

    @JsonProperty("telefone")
    @NotBlank(message = "O telefone é obrigatório")
    private String telefone;

    @JsonProperty("fotoPerfil")
    private String fotoPerfil;

    @JsonProperty("bio")
    private String bio;

    @Valid
    private UsuarioPostRequestDto usuario;
}
