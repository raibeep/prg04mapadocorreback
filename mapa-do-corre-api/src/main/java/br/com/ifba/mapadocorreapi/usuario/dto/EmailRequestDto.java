package br.com.ifba.mapadocorreapi.usuario.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequestDto {
    @JsonProperty("email")
    private String email;
}
