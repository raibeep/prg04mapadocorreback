package br.com.ifba.mapadocorreapi.auth.controller;

import br.com.ifba.mapadocorreapi.auth.dto.LoginRequestDto;
import br.com.ifba.mapadocorreapi.auth.dto.LoginResponseDto;
import br.com.ifba.mapadocorreapi.infrastructure.security.JwtUtil;
import br.com.ifba.mapadocorreapi.negocio.repository.NegocioRepository;
import br.com.ifba.mapadocorreapi.usuario.entity.Usuario;
import br.com.ifba.mapadocorreapi.usuario.repository.UsuarioRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;
    private final NegocioRepository negocioRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto dto) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha())
        );

        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String perfil = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("")
                .replace("ROLE_", "");

        String token = jwtUtil.generateToken(dto.getEmail(), perfil);

        // Verifica se é empresário e se tem negócio cadastrado
        Boolean temNegocio = false;
        if ("EMPRESARIO".equals(perfil)) {
            temNegocio = negocioRepository.existsByDonoId(usuario.getId());
        }

        return ResponseEntity.ok(new LoginResponseDto(token, perfil, usuario.getId(), temNegocio));
    }
}