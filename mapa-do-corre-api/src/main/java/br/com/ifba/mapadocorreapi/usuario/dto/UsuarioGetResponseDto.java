package br.com.ifba.mapadocorreapi.usuario.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioGetResponseDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("email")
    private String email;
}
