package br.com.ifba.mapadocorreapi.negocio.dto;

import br.com.ifba.mapadocorreapi.enums.TipoProduto;
import br.com.ifba.mapadocorreapi.usuario.dto.UsuarioPostRequestDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NegocioPostRequestDto {

    @JsonProperty("nome")
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @JsonProperty("descricao")
    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @JsonProperty("contato")
    @NotBlank(message = "O contato é obrigatório")
    private String contato;

    @JsonProperty("foto")
    private String foto;

    @JsonProperty("tipo")
    @NotNull(message = "O tipo de negócio é obrigatório")
    private TipoProduto tipo;

    @JsonProperty("categoriaId")
    @NotNull(message = "A categoria é obrigatória")
    private Long categoriaId;
}