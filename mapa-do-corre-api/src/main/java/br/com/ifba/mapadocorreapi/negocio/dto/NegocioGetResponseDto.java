package br.com.ifba.mapadocorreapi.negocio.dto;

import br.com.ifba.mapadocorreapi.enums.TipoProduto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NegocioGetResponseDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("descricao")
    private String descricao;

    @JsonProperty("contato")
    private String contato;

    @JsonProperty("foto")
    private String foto;

    @JsonProperty("tipo")
    private TipoProduto tipo;

    @JsonProperty("criadoEm")
    private Date criadoEm;

    @JsonProperty("categoriaNome")
    private String categoriaNome;

    @JsonProperty("categoriaId")
    private Long categoriaId;

    @JsonProperty("donoEmail")
    private String donoEmail;
}