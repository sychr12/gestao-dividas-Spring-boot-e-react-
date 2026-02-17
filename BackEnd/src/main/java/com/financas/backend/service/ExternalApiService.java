package com.financas.backend.service;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.financas.backend.config.ApiKeyConfig;

@Service
public class ExternalApiService {

    private final RestTemplate restTemplate;
    private final ApiKeyConfig apiKeyConfig;

    // URL da API externa (ajuste conforme necessário)
    private static final String API_BASE_URL = "https://api.exemplo.com";

    public ExternalApiService(ApiKeyConfig apiKeyConfig) {
        this.restTemplate = new RestTemplate();
        this.apiKeyConfig = apiKeyConfig;
    }

    /**
     * Busca dados da API externa usando a API Key configurada
     */
    public Map<String, Object> fetchDataFromApi(String endpoint) {
        try {
            String url = API_BASE_URL + endpoint;
            
            // Criar headers com a API Key
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKeyConfig.getApiKey());
            // Ou, dependendo da API:
            // headers.set("X-API-Key", apiKeyConfig.getApiKey());
            
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            // Fazer a requisição
            ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Map.class
            );
            
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new RuntimeException("Erro ao buscar dados da API: " + response.getStatusCode());
            }
            
        } catch (RestClientException e) {
            throw new RuntimeException("Erro ao conectar com a API externa: " + e.getMessage(), e);
        }
    }

    /**
     * POST para API externa
     */
    public Map<String, Object> postDataToApi(String endpoint, Map<String, Object> data) {
        try {
            String url = API_BASE_URL + endpoint;
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKeyConfig.getApiKey());
            
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(data, headers);
            
            ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                Map.class
            );
            
            if (response.getStatusCode() == HttpStatus.OK || 
                response.getStatusCode() == HttpStatus.CREATED) {
                return response.getBody();
            } else {
                throw new RuntimeException("Erro ao enviar dados para API: " + response.getStatusCode());
            }
            
        } catch (RestClientException e) {
            throw new RuntimeException("Erro ao conectar com a API externa: " + e.getMessage(), e);
        }
    }

    /**
     * Verifica se a API Key é válida
     */
    public boolean validateApiKey() {
        try {
            // Endpoint de validação da API (ajuste conforme necessário)
            String url = API_BASE_URL + "/validate";
            
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiKeyConfig.getApiKey());
            
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Map.class
            );
            
            return response.getStatusCode() == HttpStatus.OK;
            
        } catch (RestClientException e) {
            return false;
        }
    }
}