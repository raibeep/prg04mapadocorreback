package br.com.ifba.mapadocorreapi.client;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Raika
 * Cliente HTTP que consome o endpoint de categorias
 * via WebClient (substituto reativo do RestTemplate).
 */
@Log4j2
public class SpringClient {

    public static void main(String[] args) {

        // 1. Configura o WebClient com a URL base e header padrão
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/categorias")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        // 2. Requisição GET — lista todas as categorias (com paginação)
        // ?page=0&size=5 pode ser passado como query param
        String response = webClient.get()
                .uri("/findall?page=0&size=5")
                .retrieve()
                .bodyToMono(String.class)
                .block(); // bloqueia até receber a resposta (torna assíncrono em síncrono)

        log.info("Resposta do GET /categorias/findall: {}", response);

        // 3. Requisição POST — salva uma nova categoria
        String novaCategoria = """
                {
                    "nome": "Artesanato"
                }
                """;

        String postResponse = webClient.post()
                .uri("/save")
                .bodyValue(novaCategoria)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info("Resposta do POST /categorias/save: {}", postResponse);
    }
}
