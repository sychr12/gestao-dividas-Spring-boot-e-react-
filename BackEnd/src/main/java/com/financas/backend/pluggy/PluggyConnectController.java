package com.financas.backend.pluggy;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.financas.backend.config.ApiKeyConfig;

@RestController
@RequestMapping("/api/pluggy")
@CrossOrigin(origins = "*")
public class PluggyConnectController {

    private final ApiKeyConfig apiKeyConfig;
    private final RestTemplate restTemplate;
    
    private static final String PLUGGY_BASE_URL = "https://api.pluggy.ai";

    public PluggyConnectController(ApiKeyConfig apiKeyConfig) {
        this.apiKeyConfig = apiKeyConfig;
        this.restTemplate = new RestTemplate();
    }

    /**
     * Cria um Connect Token para o Pluggy Connect Widget
     * Este token permite que o frontend abra o modal do Pluggy
     */
    @PostMapping("/connect-token")
    public ResponseEntity<Map<String, Object>> createConnectToken() {
        try {
            String url = PLUGGY_BASE_URL + "/connect_token";
            
            // Headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-API-KEY", apiKeyConfig.getApiKey());
            
            // Body (opcional - pode adicionar clientUserId)
            Map<String, Object> requestBody = new HashMap<>();
            // requestBody.put("clientUserId", "user-123"); // Opcional
            
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            
            // Fazer requisição
            ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                Map.class
            );
            
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return ResponseEntity.ok(response.getBody());
            } else {
                throw new RuntimeException("Erro ao criar connect token");
            }
            
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            error.put("message", "Erro ao criar connect token. Verifique sua API Key.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Webhook para receber notificações do Pluggy
     * Configure este endpoint no dashboard do Pluggy
     */
    @PostMapping("/webhook")
    public ResponseEntity<Map<String, String>> handleWebhook(
            @RequestBody Map<String, Object> webhookData,
            @RequestHeader("X-Pluggy-Signature") String signature) {
        
        try {
            // TODO: Validar assinatura do webhook
            // https://docs.pluggy.ai/#webhooks-signature-validation
            
            String event = (String) webhookData.get("event");
            @SuppressWarnings("unchecked")
            Map<String, Object> data = (Map<String, Object>) webhookData.get("data");
            
            System.out.println("Webhook recebido: " + event);
            System.out.println("Dados: " + data);
            
            // Processar diferentes tipos de eventos
            switch (event) {
                case "item/created" -> handleItemCreated(data);
                case "item/updated" -> handleItemUpdated(data);
                case "item/error" -> handleItemError(data);
                case "item/deleted" -> handleItemDeleted(data);
                default -> System.out.println("Evento desconhecido: " + event);
            }
            
            Map<String, String> response = new HashMap<>();
            response.put("status", "received");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    private void handleItemCreated(Map<String, Object> data) {
        String itemId = (String) data.get("id");
        System.out.println("Item criado: " + itemId);
        // TODO: Salvar no banco
    }

    private void handleItemUpdated(Map<String, Object> data) {
        String itemId = (String) data.get("id");
        System.out.println("Item atualizado: " + itemId);
        // TODO: Buscar dados atualizados e salvar
    }

    private void handleItemError(Map<String, Object> data) {
        String itemId = (String) data.get("id");
        String error = (String) data.get("error");
        System.err.println("Erro no item " + itemId + ": " + error);
        // TODO: Notificar usuário
    }

    private void handleItemDeleted(Map<String, Object> data) {
        String itemId = (String) data.get("id");
        System.out.println("Item deletado: " + itemId);
        // TODO: Remover do banco
    }
}