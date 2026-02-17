package com.financas.backend.transaction;

import java.util.List;

import org.springframework.stereotype.Service;

import com.financas.backend.card.Card;
import com.financas.backend.card.CardRepository;

@Service
public class TransactionService {

    private final TransactionRepository repository;
    private final CardRepository cardRepository;

    public TransactionService(TransactionRepository repository, 
                             CardRepository cardRepository) {
        this.repository = repository;
        this.cardRepository = cardRepository;
    }

    public List<Transaction> getByCard(Long cardId) {
        return repository.findByCardIdOrderByDateDesc(cardId);
    }

    public Transaction create(Transaction transaction, Long cardId) {
        if (cardId == null) {
            throw new RuntimeException("ID do cartão não pode ser nulo");
        }
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado"));
        
        transaction.setCard(card);
        return repository.save(transaction);
    }

    public void delete(Long id) {
        if (id == null || !repository.existsById(id)) {
            throw new RuntimeException("Transação não encontrada");
        }
        repository.deleteById(id);
    }
}