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
    public ResponseEntity<?> update(@PathVariable Long id,
            @RequestBody @Valid ClienteUpdateRequestDto dto) {

        Cliente cliente = objectMapperUtil.map(dto, Cliente.class);

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
    public ResponseEntity<AvaliacaoGetResponseDto> avaliarNegocio(
            @PathVariable Long clienteId,
            @PathVariable Long negocioId,
            @RequestBody @Valid AvaliacaoPostRequestDto dto) {

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setNota(dto.getNota());
        avaliacao.setComentario(dto.getComentario());

        Avaliacao salva = clienteService.avaliarNegocio(clienteId, negocioId, avaliacao);

        // Converte a entidade retornada pelo service em DTO para não expor dados sensíveis na resposta
        AvaliacaoGetResponseDto response = new AvaliacaoGetResponseDto();
        response.setId(salva.getId());
        response.setNota(salva.getNota());
        response.setComentario(salva.getComentario());
        response.setResposta(salva.getResposta());
        response.setCriadoEm(salva.getCriadoEm());
        response.setAutorEmail(salva.getAutor().getEmail());
        response.setNegocioNome(salva.getNegocio().getNome());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{clienteId}/pedidos/{negocioId}")
    public ResponseEntity<PedidoGetResponseDto> realizarPedido(
            @PathVariable Long clienteId,
            @PathVariable Long negocioId,
            @RequestBody @Valid PedidoPostRequestDto dto) {

        Pedido pedido = new Pedido();
        pedido.setValorTotal(dto.getValorTotal());

        Pedido salvo = clienteService.realizarPedido(clienteId, negocioId, pedido);

        // Converte a entidade retornada pelo service em DTO para não expor dados sensíveis na resposta
        PedidoGetResponseDto response = new PedidoGetResponseDto();
        response.setId(salvo.getId());
        response.setValorTotal(salvo.getValorTotal());
        response.setStatus(salvo.getStatus());
        response.setCriadoEm(salvo.getCriadoEm());
        response.setClienteEmail(salvo.getCliente().getEmail());
        response.setNegocioNome(salvo.getNegocio().getNome());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
