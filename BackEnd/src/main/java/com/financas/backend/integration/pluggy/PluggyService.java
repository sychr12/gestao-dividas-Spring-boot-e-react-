package com.financas.backend.integration.pluggy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PluggyService {

    private static final String BASE_URL = "https://api.pluggy.ai";

    @Value("${pluggy.api.key}")
    private String apiKey;

    private WebClient webClient() {
        return WebClient.builder()
            .baseUrl(BASE_URL)
            .defaultHeader("X-API-KEY", apiKey)
            .defaultHeader("Content-Type", "application/json")
            .build();
    }

    /**
     * Criar connect token (usado pelo frontend)
     */
    public String createConnectToken() {
        String body = webClient()
            .post()
            .uri("/connect_token")
            .bodyValue("{}")
            .retrieve()
            .bodyToMono(String.class)
            .block();

        if (body == null) {
            throw new RuntimeException("Erro ao criar connect token: resposta vazia");
        }

        return body;
    }

    /**
     * Buscar contas do item
     */
    public String getAccounts(String itemId) {
        String body = webClient()
            .get()
            .uri("/items/{itemId}/accounts", itemId)
            .retrieve()
            .bodyToMono(String.class)
            .block();

        if (body == null) {
            throw new RuntimeException("Erro ao buscar contas: resposta vazia");
        }

        return body;
    }

    /**
     * Buscar transações do item
     */
    public String getTransactions(String itemId) {
        String body = webClient()
            .get()
            .uri("/items/{itemId}/transactions", itemId)
            .retrieve()
            .bodyToMono(String.class)
            .block();

        if (body == null) {
            throw new RuntimeException("Erro ao buscar transações: resposta vazia");
        }

        return body;
    }
}