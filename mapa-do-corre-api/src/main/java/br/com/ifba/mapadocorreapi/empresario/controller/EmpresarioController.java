package br.com.ifba.mapadocorreapi.empresario.controller;

import br.com.ifba.mapadocorreapi.avaliacao.dto.AvaliacaoGetResponseDto;
import br.com.ifba.mapadocorreapi.avaliacao.entity.Avaliacao;
import br.com.ifba.mapadocorreapi.categoria.entity.Categoria;
import br.com.ifba.mapadocorreapi.categoria.service.CategoriaIService;
import br.com.ifba.mapadocorreapi.empresario.dto.EmpresarioGetResponseDto;
import br.com.ifba.mapadocorreapi.empresario.dto.EmpresarioPostRequestDto;
import br.com.ifba.mapadocorreapi.empresario.dto.EmpresarioUpdateRequestDto;
import br.com.ifba.mapadocorreapi.empresario.entity.Empresario;
import br.com.ifba.mapadocorreapi.empresario.service.EmpresarioIService;
import br.com.ifba.mapadocorreapi.infrastructure.mapper.ObjectMapperUtil;
import br.com.ifba.mapadocorreapi.negocio.dto.NegocioGetResponseDto;
import br.com.ifba.mapadocorreapi.negocio.dto.NegocioPostRequestDto;
import br.com.ifba.mapadocorreapi.negocio.entity.Negocio;
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
@RequestMapping("/empresarios")
@RequiredArgsConstructor
public class EmpresarioController implements EmpresarioIController{

    private final EmpresarioIService empresarioService;
    private final CategoriaIService categoriaService;
    private final ObjectMapperUtil objectMapperUtil;

    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmpresarioGetResponseDto> save(@RequestBody @Valid EmpresarioPostRequestDto dto) {
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());

        Empresario empresario = objectMapperUtil.map(dto, Empresario.class);
        empresario.setUsuario(usuario);

        Empresario salvo = empresarioService.save(empresario);

        EmpresarioGetResponseDto response = objectMapperUtil.map(salvo, EmpresarioGetResponseDto.class);
        response.setEmail(salvo.getUsuario().getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "/findall", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<EmpresarioGetResponseDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(
                empresarioService.findAll(pageable)
                        .map(e -> {
                            EmpresarioGetResponseDto dto = objectMapperUtil.map(e, EmpresarioGetResponseDto.class);
                            dto.setEmail(e.getUsuario().getEmail());
                            return dto;
                        }));
    }

    @GetMapping("/findbyid/{id}")
    public ResponseEntity<EmpresarioGetResponseDto> findById(@PathVariable Long id) {
        Empresario empresario = empresarioService.findById(id);
        EmpresarioGetResponseDto response = objectMapperUtil.map(empresario, EmpresarioGetResponseDto.class);
        response.setEmail(empresario.getUsuario().getEmail());
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid EmpresarioUpdateRequestDto dto) {

        Empresario empresario = objectMapperUtil.map(dto, Empresario.class);

        empresarioService.update(id, empresario);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = "/update/{id}/senha")
    public ResponseEntity<Void> updateSenha(
            @PathVariable Long id,
            @RequestParam String senhaAtual,
            @RequestParam String novaSenha) {
        empresarioService.updateSenha(id, senhaAtual, novaSenha);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        empresarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{empresarioId}/negocios")
    public ResponseEntity<NegocioGetResponseDto> cadastrarNegocio(
            @PathVariable Long empresarioId,
            @RequestBody @Valid NegocioPostRequestDto dto) {

        Negocio negocio = objectMapperUtil.map(dto, Negocio.class);

        Categoria categoria = categoriaService.findById(dto.getCategoriaId());
        negocio.setCategoria(categoria);

        Negocio salvo = empresarioService.cadastrarNegocio(empresarioId, negocio);

        NegocioGetResponseDto response = objectMapperUtil.map(salvo, NegocioGetResponseDto.class);
        response.setCategoriaNome(salvo.getCategoria().getNome());
        response.setDonoEmail(salvo.getDono().getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{empresarioId}/avaliacoes/{avaliacaoId}/resposta")
    public ResponseEntity<AvaliacaoGetResponseDto> responderAvaliacao(
            @PathVariable Long empresarioId,
            @PathVariable Long avaliacaoId,
            @RequestParam String resposta) {

        Avaliacao respondida = empresarioService.responderAvaliacao(empresarioId, avaliacaoId, resposta);

        // Converte a entidade retornada pelo service em DTO para não expor dados sensíveis na resposta
        AvaliacaoGetResponseDto response = new AvaliacaoGetResponseDto();
        response.setId(respondida.getId());
        response.setNota(respondida.getNota());
        response.setComentario(respondida.getComentario());
        response.setResposta(respondida.getResposta());
        response.setCriadoEm(respondida.getCriadoEm());
        response.setAutorEmail(respondida.getAutor().getEmail());
        response.setNegocioNome(respondida.getNegocio().getNome());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{empresarioId}/negocios")
    public ResponseEntity<NegocioGetResponseDto> getNegocio(@PathVariable Long empresarioId) {
        Negocio negocio = empresarioService.getNegocioDoEmpresario(empresarioId);

        if (negocio == null) return ResponseEntity.noContent().build();

        NegocioGetResponseDto response = objectMapperUtil.map(negocio, NegocioGetResponseDto.class);
        response.setCategoriaNome(negocio.getCategoria().getNome());
        response.setDonoEmail(negocio.getDono().getEmail());

        return ResponseEntity.ok(response);
    }
}