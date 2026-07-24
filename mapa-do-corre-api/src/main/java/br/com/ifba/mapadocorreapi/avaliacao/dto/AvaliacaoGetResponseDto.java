package br.com.ifba.mapadocorreapi.avaliacao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoGetResponseDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nota")
    private Integer nota;

    @JsonProperty("comentario")
    private String comentario;

    @JsonProperty("resposta")
    private String resposta;

    @JsonProperty("criadoEm")
    private Date criadoEm;

    @JsonProperty("autorEmail")
    private String autorEmail;

    @JsonProperty("autorNome")
    private String autorNome; // Recebe o nome do cliente no React

    @JsonProperty("negocioId")
    private Long negocioId;

    @JsonProperty("negocioNome")
    private String negocioNome;
}