package br.com.ifba.mapadocorreapi.cliente.controller;

import br.com.ifba.mapadocorreapi.avaliacao.dto.AvaliacaoGetResponseDto;
import br.com.ifba.mapadocorreapi.avaliacao.dto.AvaliacaoPostRequestDto;
import br.com.ifba.mapadocorreapi.avaliacao.entity.Avaliacao;
import br.com.ifba.mapadocorreapi.cliente.dto.ClienteGetResponseDto;
import br.com.ifba.mapadocorreapi.cliente.dto.ClientePostRequestDto;
import br.com.ifba.mapadocorreapi.cliente.dto.ClienteUpdateRequestDto;
import br.com.ifba.mapadocorreapi.cliente.entity.Cliente;
import br.com.ifba.mapadocorreapi.cliente.service.ClienteIService;
import br.com.ifba.mapadocorreapi.infrastructure.mapper.ObjectMapperUtil;
import br.com.ifba.mapadocorreapi.pedido.dto.PedidoGetResponseDto;
import br.com.ifba.mapadocorreapi.pedido.dto.PedidoPostRequestDto;
import br.com.ifba.mapadocorreapi.pedido.entity.Pedido;
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

        Cliente cliente = objectMapperUtil.map(dto, Cliente.class);

        Cliente salvo = clienteService.save(cliente);

        ClienteGetResponseDto response = objectMapperUtil.map(salvo, ClienteGetResponseDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/findall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ClienteGetResponseDto>> findAll(Pageable pageable) {

        return ResponseEntity.ok(clienteService.findAll(pageable).map(c -> objectMapperUtil
                .map(c, ClienteGetResponseDto.class)));
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        return ResponseEntity.ok(objectMapperUtil.map(clienteService.findById(id), ClienteGetResponseDto.class));
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid ClienteUpdateRequestDto dto) {

        Cliente cliente = objectMapperUtil.map(dto, Cliente.class);

        clienteService.update(id, cliente);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update/{id}/senha")
    public ResponseEntity<?> updateSenha(@PathVariable Long id, @RequestParam String senhaAtual,
                                         @RequestParam String novaSenha) {

        clienteService.updateSenha(id, senhaAtual, novaSenha);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        clienteService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{clienteId}/avaliacoes/{negocioId}")
    public ResponseEntity<AvaliacaoGetResponseDto> avaliarNegocio(@PathVariable Long clienteId, @PathVariable Long negocioId,
                                                                  @RequestBody @Valid AvaliacaoPostRequestDto dto) {

        Avaliacao avaliacao = objectMapperUtil.map(dto, Avaliacao.class);

        Avaliacao salva = clienteService.avaliarNegocio(clienteId, negocioId, avaliacao);

        AvaliacaoGetResponseDto response = objectMapperUtil.map(salva, AvaliacaoGetResponseDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{clienteId}/pedidos")
    public ResponseEntity<PedidoGetResponseDto> realizarPedido(@PathVariable Long clienteId,
                                                               @RequestBody @Valid PedidoPostRequestDto dto) {

        Pedido pedido = objectMapperUtil.map(dto, Pedido.class);

        Pedido salvo = clienteService.realizarPedido(clienteId, pedido);

        PedidoGetResponseDto response = objectMapperUtil.map(salvo, PedidoGetResponseDto.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}