package br.com.ifba.mapadocorreapi.infrastructure.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jonatas Bastos
 * Classe utilitária para mapeamento de objetos usando ModelMapper.
 */
@Component
public class ObjectMapperUtil {

    private static final ModelMapper MODEL_MAPPER;

    static {
        MODEL_MAPPER = new ModelMapper();
    }

    /**
     * Converte um único objeto de Input para Output.
     *
     * @param object objeto de entrada
     * @param clazz  classe de saída
     * @return objeto convertido
     */
    public <Input, Output> Output map(final Input object, final Class<Output> clazz) {

        MODEL_MAPPER.getConfiguration()
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(
                        org.modelmapper.config.Configuration.AccessLevel.PRIVATE
                );

        Output c = MODEL_MAPPER.map(object, clazz);

        return c;
    }

    /**
     * Converte uma lista/coleção de objetos Input para uma lista de Output.
     *
     * @param objectList coleção de entrada
     * @param clazz      classe de saída
     * @return lista convertida
     */
    public <Input, Output> List<Output> mapAll(
            final Collection<Input> objectList,
            final Class<Output> clazz) {

        return objectList.stream()
                .map(object -> map(object, clazz))
                .collect(Collectors.toList());
    }
}
