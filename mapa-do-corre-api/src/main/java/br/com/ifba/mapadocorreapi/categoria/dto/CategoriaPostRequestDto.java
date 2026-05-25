package br.com.ifba.mapadocorreapi.categoria.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaPostRequestDto {
    @JsonProperty ("nome")
    private String nome;

    @JsonProperty("icone")
    private String icone;
}
