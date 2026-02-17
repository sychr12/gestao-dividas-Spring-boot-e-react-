package com.financas.backend.dashboard;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.financas.backend.card.Card;
import com.financas.backend.card.CardRepository;

@Service
public class DashboardService {

    private final CardRepository cardRepository;

    @Value("${app.api.key}")
    private String apiKeyConfigurada;

    public DashboardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    private List<Card> getUserCards(Long userId) {
        return cardRepository.findByUserId(userId);
    }

    /**
     * Validar API Key
     */
    public void validateApiKey(String apiKey) {
        if (apiKey == null || !apiKey.equals(apiKeyConfigurada)) {
            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "API Key inválida"
            );
        }
    }

    /**
     * Total de dívidas
     */
    public BigDecimal getTotalDebt(Long userId) {
        return getUserCards(userId).stream()
            .map(card -> 
                Optional.ofNullable(card.getUsedValue())
                        .orElse(BigDecimal.ZERO)
            )
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Cartão mais endividado
     */
    public Optional<Card> getMostIndebted(Long userId) {
        return getUserCards(userId).stream()
            .max(Comparator.comparing(card ->
                Optional.ofNullable(card.getUsedValue())
                        .orElse(BigDecimal.ZERO)
            ));
    }

    /**
     * Cartão com maior limite
     */
    public Optional<Card> getCardWithHighestLimit(Long userId) {
        return getUserCards(userId).stream()
            .max(Comparator.comparing(card ->
                Optional.ofNullable(card.getLimitValue())
                        .orElse(BigDecimal.ZERO)
            ));
    }

    /**
     * Ranking por uso
     */
    public List<Card> rankingByUsage(Long userId) {
        return getUserCards(userId).stream()
            .sorted(
                Comparator.comparing(
                    (Card card) -> Optional.ofNullable(card.getUsedValue())
                                           .orElse(BigDecimal.ZERO)
                ).reversed()
            )
            .toList();
    }

    /**
     * Dados do gráfico
     */
    public List<Map<String, Object>> chartData(Long userId) {
        return getUserCards(userId).stream()
            .map(card -> {
                Map<String, Object> map = new HashMap<>();
                map.put("name", card.getBank());
                map.put("value", Optional.ofNullable(card.getUsedValue())
                                         .orElse(BigDecimal.ZERO));
                return map;
            })
            .toList();
    }
}
