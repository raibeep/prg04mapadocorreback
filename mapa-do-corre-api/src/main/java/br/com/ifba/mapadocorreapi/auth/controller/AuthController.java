package br.com.ifba.mapadocorreapi.auth.controller;

import br.com.ifba.mapadocorreapi.auth.dto.LoginRequestDto;
import br.com.ifba.mapadocorreapi.auth.dto.LoginResponseDto;
import br.com.ifba.mapadocorreapi.cliente.repository.ClienteRepository;
import br.com.ifba.mapadocorreapi.empresario.repository.EmpresarioRepository;
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
    private final ClienteRepository clienteRepository;
    private final EmpresarioRepository empresarioRepository;


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

        Boolean temNegocio = false;
        Long perfilId = null;
        String nome = "";

        if ("EMPRESARIO".equals(perfil)) {
            var empresario = empresarioRepository.findByUsuarioId(usuario.getId()).orElse(null);
            if (empresario != null) {
                perfilId = empresario.getId();
                nome = empresario.getNome();
                temNegocio = negocioRepository.existsByDonoId(empresario.getId());
            }
        } else if ("CLIENTE".equals(perfil)) {
            var cliente = clienteRepository.findByUsuarioId(usuario.getId()).orElse(null);
            if (cliente != null) {
                perfilId = cliente.getId();
                nome = cliente.getNome();
            }
        }

        return ResponseEntity.ok(new LoginResponseDto(token, perfil, usuario.getId(), perfilId, temNegocio, nome));
    }
}