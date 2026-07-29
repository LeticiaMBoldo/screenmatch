package br.com.alura.screenmatch.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClient;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class ConsultaChatGPT {

    private final RestClient client;
    private final ObjectMapper mapper = new ObjectMapper();

    public ConsultaChatGPT() {

        Properties properties = new Properties();

        try (InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            properties.load(input);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar application.properties", e);
        }


        String apiKey = properties.getProperty("openai.api.key");


        client = RestClient.builder()
                .baseUrl("https://api.openai.com/v1")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public String obterTraducao(String texto) {

        Map<String, Object> body = Map.of(
                "model", "gpt-5-mini",
                "input", "Traduza para português:\n\n" + texto
        );

        String resposta = client.post()
                .uri("/responses")
                .body(body)
                .retrieve()
                .body(String.class);

        try {

            JsonNode json = mapper.readTree(resposta);

            return json
                    .path("output")
                    .get(0)
                    .path("content")
                    .get(0)
                    .path("text")
                    .asText();

        } catch (Exception e) {

            throw new RuntimeException("Erro ao interpretar resposta da OpenAI", e);

        }

    }

}