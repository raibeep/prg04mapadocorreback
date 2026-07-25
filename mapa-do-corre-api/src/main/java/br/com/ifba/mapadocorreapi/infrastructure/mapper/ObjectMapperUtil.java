package br.com.ifba.mapadocorreapi.infrastructure.mapper;

import br.com.ifba.mapadocorreapi.avaliacao.dto.AvaliacaoGetResponseDto;
import br.com.ifba.mapadocorreapi.avaliacao.dto.AvaliacaoPostRequestDto;
import br.com.ifba.mapadocorreapi.avaliacao.entity.Avaliacao;
import br.com.ifba.mapadocorreapi.cliente.dto.ClienteGetResponseDto;
import br.com.ifba.mapadocorreapi.cliente.entity.Cliente;
import br.com.ifba.mapadocorreapi.empresario.dto.EmpresarioGetResponseDto;
import br.com.ifba.mapadocorreapi.empresario.entity.Empresario;
import br.com.ifba.mapadocorreapi.endereco.dto.EnderecoGetResponseDto;
import br.com.ifba.mapadocorreapi.endereco.dto.EnderecoPostRequestDto;
import br.com.ifba.mapadocorreapi.endereco.entity.Endereco;
import br.com.ifba.mapadocorreapi.itempedido.dto.ItemPedidoGetResponseDto;
import br.com.ifba.mapadocorreapi.itempedido.dto.ItemPedidoPostRequestDto;
import br.com.ifba.mapadocorreapi.itempedido.entity.ItemPedido;
import br.com.ifba.mapadocorreapi.negocio.dto.NegocioGetResponseDto;
import br.com.ifba.mapadocorreapi.negocio.entity.Negocio;
import br.com.ifba.mapadocorreapi.pedido.dto.PedidoGetResponseDto;
import br.com.ifba.mapadocorreapi.pedido.dto.PedidoPostRequestDto;
import br.com.ifba.mapadocorreapi.pedido.entity.Pedido;
import br.com.ifba.mapadocorreapi.produto.dto.ProdutoGetResponseDto;
import br.com.ifba.mapadocorreapi.produto.entity.Produto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe utilitária para mapeamento de objetos usando ModelMapper.
 */
@Component
public class ObjectMapperUtil {

    private static final ModelMapper MODEL_MAPPER;

    static {

        MODEL_MAPPER = new ModelMapper();

        MODEL_MAPPER.getConfiguration()
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        MODEL_MAPPER.typeMap(Produto.class, ProdutoGetResponseDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getNegocio().getId(), ProdutoGetResponseDto::setNegocioId);
            mapper.map(src -> src.getNegocio().getNome(), ProdutoGetResponseDto::setNomeNegocio);
        });

        MODEL_MAPPER.typeMap(Pedido.class, PedidoGetResponseDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getCliente().getId(), PedidoGetResponseDto::setClienteId);
            mapper.map(src -> src.getCliente().getNome(), PedidoGetResponseDto::setClienteNome);
            mapper.map(src -> src.getEndereco().getId(), PedidoGetResponseDto::setEnderecoId);
            mapper.map(src -> src.getEndereco().getCidade(), PedidoGetResponseDto::setCidadeEntrega);
        });

        MODEL_MAPPER.typeMap(ItemPedido.class, ItemPedidoGetResponseDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getPedido().getId(), ItemPedidoGetResponseDto::setPedidoId);
            mapper.map(src -> src.getPedido().getCriadoEm(), ItemPedidoGetResponseDto::setCriadoEm);
            mapper.map(src -> src.getPedido().getCliente().getNome(), ItemPedidoGetResponseDto::setClienteNome);
            mapper.map(src -> src.getPedido().getEndereco().getCidade(), ItemPedidoGetResponseDto::setEnderecoResumo);
            mapper.map(src -> src.getProduto().getId(), ItemPedidoGetResponseDto::setProdutoId);
            mapper.map(src -> src.getProduto().getNome(), ItemPedidoGetResponseDto::setNomeProduto);
            mapper.map(src -> src.getProduto().getFoto(), ItemPedidoGetResponseDto::setFotoProduto);
            mapper.map(src -> src.getProduto().getNegocio().getId(), ItemPedidoGetResponseDto::setNegocioId);
            mapper.map(src -> src.getProduto().getNegocio().getNome(), ItemPedidoGetResponseDto::setNegocioNome);
        });

        MODEL_MAPPER.typeMap(Endereco.class, EnderecoGetResponseDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getCliente().getId(), EnderecoGetResponseDto::setClienteId);
            mapper.map(src -> src.getNegocio().getId(), EnderecoGetResponseDto::setNegocioId);
            mapper.map(src -> src.getNegocio().getNome(), EnderecoGetResponseDto::setNomeNegocio);
        });

        MODEL_MAPPER.typeMap(Avaliacao.class, AvaliacaoGetResponseDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getAutor().getEmail(), AvaliacaoGetResponseDto::setAutorEmail);
            mapper.map(src -> src.getNegocio().getId(), AvaliacaoGetResponseDto::setNegocioId);
            mapper.map(src -> src.getNegocio().getNome(), AvaliacaoGetResponseDto::setNegocioNome);
        });

        MODEL_MAPPER.typeMap(Empresario.class, EmpresarioGetResponseDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getUsuario().getEmail(), EmpresarioGetResponseDto::setEmail);
        });

        MODEL_MAPPER.typeMap(Cliente.class, ClienteGetResponseDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getUsuario().getEmail(), ClienteGetResponseDto::setEmail);
        });

        MODEL_MAPPER.typeMap(Negocio.class, NegocioGetResponseDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getCategoria().getId(), NegocioGetResponseDto::setCategoriaId);
            mapper.map(src -> src.getCategoria().getNome(), NegocioGetResponseDto::setCategoriaNome);
            mapper.map(src -> src.getDono().getUsuario().getEmail(), NegocioGetResponseDto::setDonoEmail);
        });

        // Converters existentes preservados
        Converter<PedidoPostRequestDto, Pedido> pedidoConverter = context -> {
            PedidoPostRequestDto source = context.getSource();
            if (source == null) return null;

            Pedido pedido = new Pedido();
            pedido.setMetodoPagamento(source.getMetodoPagamento());
            pedido.setTroco(source.getTroco());

            if (source.getEnderecoId() != null) {
                Endereco endereco = new Endereco();
                endereco.setId(source.getEnderecoId());
                pedido.setEndereco(endereco);
            }

            if (source.getItens() != null) {
                for (ItemPedidoPostRequestDto itemDto : source.getItens()) {
                    ItemPedido item = new ItemPedido();
                    item.setQuantidade(itemDto.getQuantidade());

                    Produto produto = new Produto();
                    produto.setId(itemDto.getProdutoId());

                    item.setProduto(produto);
                    item.setPedido(pedido);

                    pedido.getItens().add(item);
                }
            }
            return pedido;
        };

        MODEL_MAPPER.addConverter(pedidoConverter);

        Converter<EnderecoPostRequestDto, Endereco> enderecoConverter = context -> {
            EnderecoPostRequestDto source = context.getSource();
            if (source == null) return null;

            Endereco endereco = new Endereco();
            endereco.setCep(source.getCep());
            endereco.setRua(source.getRua());
            endereco.setNumero(source.getNumero());
            endereco.setComplemento(source.getComplemento());
            endereco.setBairro(source.getBairro());
            endereco.setCidade(source.getCidade());
            endereco.setEstado(source.getEstado());

            if (source.getClienteId() != null) {
                Cliente cliente = new Cliente();
                cliente.setId(source.getClienteId());
                endereco.setCliente(cliente);
            }

            return endereco;
        };

        MODEL_MAPPER.addConverter(enderecoConverter);

        Converter<AvaliacaoPostRequestDto, Avaliacao> avaliacaoConverter = context -> {

            AvaliacaoPostRequestDto source = context.getSource();

            if (source == null) {
                return null;
            }

            Avaliacao avaliacao = new Avaliacao();

            avaliacao.setNota(source.getNota());
            avaliacao.setComentario(source.getComentario());

            if (source.getNegocioId() != null) {
                Negocio negocio = new Negocio();
                negocio.setId(source.getNegocioId());
                avaliacao.setNegocio(negocio);
            }

            return avaliacao;
        };

        MODEL_MAPPER.addConverter(avaliacaoConverter);
    }

    public <Input, Output> Output map(final Input object, final Class<Output> clazz) {
        return MODEL_MAPPER.map(object, clazz);
    }

    public <Input, Output> List<Output> mapAll(final Collection<Input> objectList, final Class<Output> clazz) {
        return objectList.stream().map(object -> map(object, clazz)).collect(Collectors.toList());
    }
}