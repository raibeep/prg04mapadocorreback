package br.com.ifba.mapadocorreapi.cliente.controller;

import br.com.ifba.mapadocorreapi.avaliacao.entity.Avaliacao;
import br.com.ifba.mapadocorreapi.cliente.dto.ClienteGetResponseDto;
import br.com.ifba.mapadocorreapi.cliente.dto.ClientePostRequestDto;
import br.com.ifba.mapadocorreapi.cliente.entity.Cliente;
import br.com.ifba.mapadocorreapi.cliente.service.ClienteIService;
import br.com.ifba.mapadocorreapi.infrastructure.mapper.ObjectMapperUtil;
import br.com.ifba.mapadocorreapi.pedido.entity.Pedido;
import br.com.ifba.mapadocorreapi.usuario.entity.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController implements ClienteIController {
    private final ClienteIService clienteService;
    private final ObjectMapperUtil objectMapperUtil;

    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody @Valid ClientePostRequestDto dto) {
        // Monta o Usuario manualmente a partir do DTO
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());

        // Monta o Cliente com os dados pessoais
        Cliente cliente = objectMapperUtil.map(dto, Cliente.class);
        cliente.setUsuario(usuario);

        Cliente salvo = clienteService.save(cliente);
        // Monta o response manualmente também
        ClienteGetResponseDto response = objectMapperUtil.map(salvo, ClienteGetResponseDto.class);
        response.setEmail(salvo.getUsuario().getEmail()); // ✅ pega o email do Usuario

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/findall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ClienteGetResponseDto>> findAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(clienteService.findAll(pageable)
                        .map(c -> objectMapperUtil.map(c, ClienteGetResponseDto.class)));
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(objectMapperUtil.map(
                        clienteService.findById(id),
                        ClienteGetResponseDto.class));
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid ClientePostRequestDto dto) {
        // Monta o Usuario manualmente a partir do DTO
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());

        // Monta o Cliente com os dados pessoais
        Cliente cliente = objectMapperUtil.map(dto, Cliente.class);
        cliente.setUsuario(usuario);

        clienteService.update(id, cliente);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = "/update/{id}/senha", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateSenha(@PathVariable Long id,
                                         @RequestParam String senhaAtual,
                                         @RequestParam String novaSenha) {
        clienteService.updateSenha(id, senhaAtual, novaSenha);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        clienteService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{clienteId}/avaliacoes/{negocioId}")
    public ResponseEntity<Avaliacao> avaliarNegocio(
            @PathVariable Long clienteId,
            @PathVariable Long negocioId,
            @RequestBody @Valid Avaliacao avaliacao) {

        Avaliacao salva = clienteService.avaliarNegocio(clienteId, negocioId, avaliacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @PostMapping("/{clienteId}/pedidos/{negocioId}")
    public ResponseEntity<Pedido> realizarPedido(
            @PathVariable Long clienteId,
            @PathVariable Long negocioId,
            @RequestBody @Valid Pedido pedido) {

        Pedido salvo = clienteService.realizarPedido(clienteId, negocioId, pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }
}
