package br.com.ifba.mapadocorreapi.pedido.controller;

import br.com.ifba.mapadocorreapi.cliente.service.ClienteIService;
import br.com.ifba.mapadocorreapi.infrastructure.mapper.ObjectMapperUtil;
import br.com.ifba.mapadocorreapi.negocio.service.NegocioIService;
import br.com.ifba.mapadocorreapi.pedido.dto.PedidoGetResponseDto;
import br.com.ifba.mapadocorreapi.pedido.dto.PedidoPostRequestDto;
import br.com.ifba.mapadocorreapi.pedido.entity.Pedido;
import br.com.ifba.mapadocorreapi.pedido.service.PedidoIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController implements PedidoIController {

    private final PedidoIService pedidoService;
    private final ObjectMapperUtil objectMapperUtil;

    @PostMapping(path="/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody @Valid PedidoPostRequestDto dto){

        return ResponseEntity.status(HttpStatus.CREATED).body(objectMapperUtil.map(pedidoService.save(objectMapperUtil
                .map(dto,Pedido.class)), PedidoGetResponseDto.class));
    }

    @GetMapping("/findall")
    public ResponseEntity<Page<PedidoGetResponseDto>> findAll(Pageable pageable){

        return ResponseEntity.ok(pedidoService.findAll(pageable).map(p->objectMapperUtil
                .map(p,PedidoGetResponseDto.class)));
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){

        return ResponseEntity.ok(objectMapperUtil.map(pedidoService.findById(id), PedidoGetResponseDto.class));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid PedidoPostRequestDto dto){

        pedidoService.update(id, objectMapperUtil.map(dto,Pedido.class));

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        pedidoService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/confirmar/{id}")
    public ResponseEntity<?> confirmarPedido(@PathVariable Long id){

        return ResponseEntity.ok(objectMapperUtil.map(pedidoService.confirmarPedido(id), PedidoGetResponseDto.class));
    }

    @PatchMapping("/cancelar/{id}")
    public ResponseEntity<?> cancelarPedido(@PathVariable Long id){

        return ResponseEntity.ok(objectMapperUtil.map(pedidoService.cancelarPedido(id), PedidoGetResponseDto.class));
    }

    @GetMapping("/meus-pedidos")
    public ResponseEntity<?> meusPedidos() {

        return ResponseEntity.ok(objectMapperUtil.mapAll(pedidoService.findMeusPedidos(),
                PedidoGetResponseDto.class));

    }
}