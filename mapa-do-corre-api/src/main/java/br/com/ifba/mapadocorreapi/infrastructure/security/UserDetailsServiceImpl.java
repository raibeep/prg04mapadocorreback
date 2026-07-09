package br.com.ifba.mapadocorreapi.infrastructure.security;

import br.com.ifba.mapadocorreapi.usuario.entity.Usuario;
import br.com.ifba.mapadocorreapi.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmailWithPerfil(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

        // Passa o perfil como role pro Spring Security
        String role = "ROLE_" + usuario.getPerfil().getNivelAcesso().name();

        return new org.springframework.security.core.userdetails.User(
                usuario.getEmail(),
                usuario.getSenha(),
                List.of(new SimpleGrantedAuthority(role))
        );
    }
}