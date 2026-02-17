package com.financas.backend.card;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class CardService {

    private final CardRepository repository;

    public CardService(CardRepository repository) {
        this.repository = repository;
    }

    public List<Card> getCardsByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    public Card getMostUsedCard(Long userId) {
        return repository.findByUserId(userId)
            .stream()
            .filter(card -> card.getUsedValue() != null)
            .max(Comparator.comparing(Card::getUsedValue))
            .orElse(null);
    }
    
    public Optional<Card> getCardById(Long id) {
        return repository.findById(id);
    }
    
    public Card save(Card card) {
        if (card.getName() == null || card.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do cartão é obrigatório");
        }
        if (card.getLastFourDigits() == null || card.getLastFourDigits().length() != 4) {
            throw new IllegalArgumentException("Últimos 4 dígitos devem ter exatamente 4 caracteres");
        }
        return repository.save(card);
    }
    
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Cartão não encontrado");
        }
        repository.deleteById(id);
    }
    
    public Card updateUsedValue(Long cardId, BigDecimal newUsedValue) {
        Card card = repository.findById(cardId)
            .orElseThrow(() -> new RuntimeException("Cartão não encontrado"));
        card.setUsedValue(newUsedValue);
        return repository.save(card);
    }
    
    public List<Card> getHighRiskCards(Long userId, double threshold) {
        return repository.findByUserId(userId)
            .stream()
            .filter(card -> card.getUsagePercentage() > threshold)
            .sorted(Comparator.comparing(Card::getUsagePercentage).reversed())
            .toList();
    }
}