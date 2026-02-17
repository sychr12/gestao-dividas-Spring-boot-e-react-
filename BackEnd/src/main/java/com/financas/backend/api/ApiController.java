package com.financas.backend.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financas.backend.config.ApiKeyConfig;
import com.financas.backend.service.ExternalApiService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApiController {

    private final ExternalApiService externalApiService;
    private final ApiKeyConfig apiKeyConfig;

    public ApiController(ExternalApiService externalApiService, ApiKeyConfig apiKeyConfig) {
        this.externalApiService = externalApiService;
        this.apiKeyConfig = apiKeyConfig;
    }

    /**
     * Endpoint para testar se a API Key está configurada
     */
    @GetMapping("/check-api-key")
    public ResponseEntity<Map<String, Object>> checkApiKey() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean isConfigured = apiKeyConfig.isApiKeyConfigured();
            response.put("configured", isConfigured);
            
            if (isConfigured) {
                // Não retornar a chave completa por segurança
                String apiKey = apiKeyConfig.getApiKey();
                String maskedKey = apiKey.substring(0, Math.min(10, apiKey.length())) + "..." + 
                                  apiKey.substring(Math.max(0, apiKey.length() - 10));
                response.put("apiKey", maskedKey);
                response.put("message", "API Key configurada com sucesso");
            } else {
                response.put("message", "API Key não configurada");
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Endpoint para validar API Key com a API externa
     */
    @GetMapping("/validate-api-key")
    public ResponseEntity<Map<String, Object>> validateApiKey() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean isValid = externalApiService.validateApiKey();
            response.put("valid", isValid);
            response.put("message", isValid ? "API Key válida" : "API Key inválida");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Endpoint genérico para buscar dados da API externa
     */
    @GetMapping("/fetch/{endpoint}")
    public ResponseEntity<Map<String, Object>> fetchData(@PathVariable String endpoint) {
        try {
            Map<String, Object> data = externalApiService.fetchDataFromApi("/" + endpoint);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Endpoint para enviar dados para API externa
     */
    @PostMapping("/send/{endpoint}")
    public ResponseEntity<Map<String, Object>> sendData(
            @PathVariable String endpoint,
            @RequestBody Map<String, Object> data) {
        try {
            Map<String, Object> response = externalApiService.postDataToApi("/" + endpoint, data);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}