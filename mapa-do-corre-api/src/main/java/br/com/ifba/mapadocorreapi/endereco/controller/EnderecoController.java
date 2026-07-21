package br.com.ifba.mapadocorreapi.endereco.controller;

import br.com.ifba.mapadocorreapi.endereco.dto.EnderecoGetResponseDto;
import br.com.ifba.mapadocorreapi.endereco.dto.EnderecoPostRequestDto;
import br.com.ifba.mapadocorreapi.endereco.entity.Endereco;
import br.com.ifba.mapadocorreapi.endereco.service.EnderecoIService;
import br.com.ifba.mapadocorreapi.infrastructure.mapper.ObjectMapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enderecos")
@RequiredArgsConstructor
public class EnderecoController implements EnderecoIController {

    private final EnderecoIService enderecoService;
    private final ObjectMapperUtil objectMapperUtil;

    @Override
    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody @Valid EnderecoPostRequestDto dto) {

        Endereco endereco = objectMapperUtil.map(dto, Endereco.class);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.map(enderecoService.save(endereco),
                        EnderecoGetResponseDto.class));
    }

    @Override
    @GetMapping("/findall")
    public ResponseEntity<Page<EnderecoGetResponseDto>> findAll(Pageable pageable) {

        return ResponseEntity.ok(
                enderecoService.findAll(pageable)
                        .map(e -> objectMapperUtil.map(e, EnderecoGetResponseDto.class))
        );
    }

    @GetMapping("/findbycliente/{clienteId}")
    public ResponseEntity<Page<EnderecoGetResponseDto>> findByCliente(
            @PathVariable Long clienteId,
            Pageable pageable) {

        return ResponseEntity.ok(
                enderecoService.findByClienteId(clienteId, pageable)
                        .map(e -> objectMapperUtil.map(e, EnderecoGetResponseDto.class))
        );
    }

    @Override
    @GetMapping("/findbyid/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        return ResponseEntity.ok(
                objectMapperUtil.map(
                        enderecoService.findById(id),
                        EnderecoGetResponseDto.class
                )
        );
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody @Valid EnderecoPostRequestDto dto) {

        Endereco endereco = objectMapperUtil.map(dto, Endereco.class);

        enderecoService.update(id, endereco);

        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        enderecoService.delete(id);

        return ResponseEntity.noContent().build();
    }

}