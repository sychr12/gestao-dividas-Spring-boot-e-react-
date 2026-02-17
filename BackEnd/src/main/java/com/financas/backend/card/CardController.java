package com.financas.backend.card;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cards")
@CrossOrigin(origins = "*")
public class CardController {

    private final CardService service;

    public CardController(CardService service) {
        this.service = service;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Card>> listByUser(@PathVariable Long userId) {
        List<Card> cards = service.getCardsByUser(userId);
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/user/{userId}/most-used")
    public ResponseEntity<Card> mostUsed(@PathVariable Long userId) {
        Card card = service.getMostUsedCard(userId);
        if (card == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(card);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Card> getById(@PathVariable Long id) {
        return service.getCardById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Card> create(@Valid @RequestBody Card card) {
        Card saved = service.save(card);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Card> update(@PathVariable Long id, @Valid @RequestBody Card card) {
        if (!service.getCardById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        card.setId(id);
        Card updated = service.save(card);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/user/{userId}/high-risk")
    public ResponseEntity<List<Card>> getHighRiskCards(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "80.0") double threshold) {
        List<Card> cards = service.getHighRiskCards(userId, threshold);
        return ResponseEntity.ok(cards);
    }
}