package br.com.ifba.mapadocorreapi.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {
    private String token;
    private String perfil;
    private Long id;
    private Long perfilId;
    private Boolean temNegocio;
    private String nome;
}