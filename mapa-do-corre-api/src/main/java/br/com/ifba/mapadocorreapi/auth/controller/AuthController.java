package br.com.ifba.mapadocorreapi.auth.controller;

import br.com.ifba.mapadocorreapi.auth.dto.LoginRequestDto;
import br.com.ifba.mapadocorreapi.auth.dto.LoginResponseDto;
import br.com.ifba.mapadocorreapi.infrastructure.security.JwtUtil;
import br.com.ifba.mapadocorreapi.usuario.entity.Usuario;
import br.com.ifba.mapadocorreapi.usuario.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto dto) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha())
        );

        // Busca o usuário pelo email (Garante o ID sem erro de Cast)
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Pega o perfil
        String perfil = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("")
                .replace("ROLE_", "");

        //Gera o token
        String token = jwtUtil.generateToken(dto.getEmail(), perfil);

        // Retorna o DTO com os 3 campos na ordem certa
        return ResponseEntity.ok(new LoginResponseDto(token, perfil, usuario.getId()));
    }
}