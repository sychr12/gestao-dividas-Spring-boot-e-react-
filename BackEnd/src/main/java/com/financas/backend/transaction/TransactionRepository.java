package com.financas.backend.transaction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCardId(Long cardId);
    List<Transaction> findByCardIdOrderByDateDesc(Long cardId);
}