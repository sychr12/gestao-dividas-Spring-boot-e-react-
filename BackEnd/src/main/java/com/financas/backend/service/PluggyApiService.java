package com.financas.backend.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.financas.backend.config.ApiKeyConfig;

/**
 * Serviço para integração com Pluggy API (Open Banking)
 * Documentação: https://docs.pluggy.ai/
 */
@Service
public class PluggyApiService {

    private final RestTemplate restTemplate;
    private final ApiKeyConfig apiKeyConfig;

    private static final String PLUGGY_BASE_URL = "https://api.pluggy.ai";

    public PluggyApiService(ApiKeyConfig apiKeyConfig) {
        this.restTemplate = new RestTemplate();
        this.apiKeyConfig = apiKeyConfig;
    }

    /**
     * Busca conectores disponíveis (bancos)
     */
    public Map<String, Object> getConnectors() {
        String url = PLUGGY_BASE_URL + "/connectors";
        
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<Map> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            Map.class
        );
        
        return response.getBody();
    }

    /**
     * Busca contas bancárias do usuário
     */
    public Map<String, Object> getAccounts(String itemId) {
        String url = PLUGGY_BASE_URL + "/accounts?itemId=" + itemId;
        
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<Map> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            Map.class
        );
        
        return response.getBody();
    }

    /**
     * Busca transações de uma conta
     */
    public Map<String, Object> getTransactions(String accountId, int pageSize) {
        String url = String.format("%s/transactions?accountId=%s&pageSize=%d", 
                                   PLUGGY_BASE_URL, accountId, pageSize);
        
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<Map> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            Map.class
        );
        
        return response.getBody();
    }

    /**
     * Busca cartões de crédito
     */
    public Map<String, Object> getCreditCards(String itemId) {
        String url = PLUGGY_BASE_URL + "/credit-cards?itemId=" + itemId;
        
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<Map> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            Map.class
        );
        
        return response.getBody();
    }

    /**
     * Cria um item (conexão com banco)
     */
    public Map<String, Object> createItem(String connectorId, Map<String, String> credentials) {
        String url = PLUGGY_BASE_URL + "/items";
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("connectorId", connectorId);
        requestBody.put("credentials", credentials);
        
        HttpHeaders headers = createHeaders();
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        
        ResponseEntity<Map> response = restTemplate.exchange(
            url,
            HttpMethod.POST,
            entity,
            Map.class
        );
        
        return response.getBody();
    }

    /**
     * Atualiza um item (sincroniza dados)
     */
    public Map<String, Object> updateItem(String itemId) {
        String url = PLUGGY_BASE_URL + "/items/" + itemId;
        
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<Map> response = restTemplate.exchange(
            url,
            HttpMethod.PATCH,
            entity,
            Map.class
        );
        
        return response.getBody();
    }

    /**
     * Deleta um item
     */
    public void deleteItem(String itemId) {
        String url = PLUGGY_BASE_URL + "/items/" + itemId;
        
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        restTemplate.exchange(
            url,
            HttpMethod.DELETE,
            entity,
            Void.class
        );
    }

    /**
     * Cria headers com a API Key
     */
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-KEY", apiKeyConfig.getApiKey());
        return headers;
    }

    /**
     * Verifica se a API Key é válida testando um endpoint
     */
    public boolean isApiKeyValid() {
        try {
            getConnectors();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}