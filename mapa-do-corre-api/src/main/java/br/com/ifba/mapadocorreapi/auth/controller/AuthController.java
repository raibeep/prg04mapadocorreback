package br.com.ifba.mapadocorreapi.auth.controller;

import br.com.ifba.mapadocorreapi.auth.dto.LoginRequestDto;
import br.com.ifba.mapadocorreapi.auth.dto.LoginResponseDto;
import br.com.ifba.mapadocorreapi.infrastructure.security.JwtUtil;
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

        // Autentica email e senha pelo Spring Security
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha())
        );

        // Pega o perfil do usuário autenticado
        String perfil = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("")
                .replace("ROLE_", "");

        String token = jwtUtil.generateToken(dto.getEmail(), perfil);

        return ResponseEntity.ok(new LoginResponseDto(token, perfil));
    }
}