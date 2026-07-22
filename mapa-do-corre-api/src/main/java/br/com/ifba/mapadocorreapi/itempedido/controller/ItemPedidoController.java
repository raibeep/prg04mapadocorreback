package br.com.ifba.mapadocorreapi.itempedido.controller;

import br.com.ifba.mapadocorreapi.infrastructure.mapper.ObjectMapperUtil;
import br.com.ifba.mapadocorreapi.itempedido.dto.ItemPedidoGetResponseDto;
import br.com.ifba.mapadocorreapi.itempedido.dto.ItemPedidoPostRequestDto;
import br.com.ifba.mapadocorreapi.itempedido.entity.ItemPedido;
import br.com.ifba.mapadocorreapi.itempedido.service.ItemPedidoIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/itens-pedido")
@RequiredArgsConstructor
public class ItemPedidoController implements ItemPedidoIController {

    private final ItemPedidoIService itemPedidoService;
    private final ObjectMapperUtil objectMapperUtil;

    @PostMapping(
            path="/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody @Valid ItemPedidoPostRequestDto dto){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        objectMapperUtil.map(
                                itemPedidoService.save(
                                        objectMapperUtil.map(dto, ItemPedido.class)
                                ),
                                ItemPedidoGetResponseDto.class
                        )
                );
    }

    @GetMapping("/findall")
    public ResponseEntity<Page<ItemPedidoGetResponseDto>> findAll(Pageable pageable){

        return ResponseEntity.ok(
                itemPedidoService.findAll(pageable)
                        .map(i->objectMapperUtil.map(i,ItemPedidoGetResponseDto.class))
        );
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){

        return ResponseEntity.ok(
                objectMapperUtil.map(
                        itemPedidoService.findById(id),
                        ItemPedidoGetResponseDto.class
                )
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody @Valid ItemPedidoPostRequestDto dto){

        itemPedidoService.update(
                id,
                objectMapperUtil.map(dto,ItemPedido.class)
        );

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        itemPedidoService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/negocio/{negocioId}")
    public ResponseEntity<?> findByNegocio(@PathVariable Long negocioId) {

        return ResponseEntity.ok(objectMapperUtil.mapAll(itemPedidoService.findByNegocio(negocioId),
                        ItemPedidoGetResponseDto.class));
    }

    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<?> confirmar(@PathVariable Long id) {

        return ResponseEntity.ok(objectMapperUtil.map(itemPedidoService.confirmarItem(id),
                        ItemPedidoGetResponseDto.class));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelar(@PathVariable Long id) {

        return ResponseEntity.ok(objectMapperUtil.map(itemPedidoService.cancelarItem(id),
                        ItemPedidoGetResponseDto.class));
    }
}
