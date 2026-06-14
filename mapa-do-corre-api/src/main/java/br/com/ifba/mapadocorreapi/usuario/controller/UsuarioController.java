package br.com.ifba.mapadocorreapi.usuario.controller;

import br.com.ifba.mapadocorreapi.infrastructure.mapper.ObjectMapperUtil;
import br.com.ifba.mapadocorreapi.usuario.dto.UsuarioGetResponseDto;
import br.com.ifba.mapadocorreapi.usuario.dto.UsuarioPostRequestDto;
import br.com.ifba.mapadocorreapi.usuario.entity.Usuario;
import br.com.ifba.mapadocorreapi.usuario.service.UsuarioIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController implements UsuarioIController{

    private final UsuarioIService usuarioService;
    private final ObjectMapperUtil objectMapperUtil;

    @GetMapping(path = "/findall", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<Page<UsuarioGetResponseDto>> findAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(usuarioService.findAll(pageable)
                        .map(c -> objectMapperUtil.map
                                (c, UsuarioGetResponseDto.class)));
    }

    @GetMapping("/findbyid/{id}")
    @Override
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(objectMapperUtil.map(
                        usuarioService.findById(id),
                        UsuarioGetResponseDto.class));
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid UsuarioPostRequestDto usuarioPostRequestDto) {
        usuarioService.update(id, objectMapperUtil.map(usuarioPostRequestDto, Usuario.class));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> delete(@PathVariable Long id) {

        usuarioService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
