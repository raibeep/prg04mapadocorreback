package br.com.ifba.mapadocorreapi.empresario.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpresarioPostRequestDto {

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

    @JsonProperty("email")
    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Informe um email válido")
    private String email;

    @JsonProperty("senha")
    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    private String senha;
}