package br.com.ifba.mapadocorreapi.auth.controller;

import br.com.ifba.mapadocorreapi.auth.dto.LoginRequestDto;
import br.com.ifba.mapadocorreapi.auth.dto.LoginResponseDto;
import br.com.ifba.mapadocorreapi.infrastructure.security.JwtUtil;
import br.com.ifba.mapadocorreapi.usuario.entity.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto dto) {
        // 1. Autentica (se a senha estiver errada, o Spring já barra aqui)
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha())
        );

        // 2. Pega o perfil de forma simples
        String perfil = auth.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");

        // 3. Gera o token
        String token = jwtUtil.generateToken(dto.getEmail(), perfil);

        // 4. Retorna a resposta (Se o ID estiver dando erro, mandamos o e-mail no lugar ou null para não travar o login)
        return ResponseEntity.ok(new LoginResponseDto(token, perfil, null));
    }

}