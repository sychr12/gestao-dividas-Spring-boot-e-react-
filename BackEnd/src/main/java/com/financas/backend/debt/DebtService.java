package com.financas.backend.debt;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class DebtService {
    
    private final DebtRepository repository;

    public DebtService(DebtRepository repository) {
        this.repository = repository;
    }

    public Debt salvar(Debt debt) {
        if (debt == null) {
            throw new IllegalArgumentException("Dívida não pode ser nula");
        }
        if (debt.getDescription() == null || debt.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição é obrigatória");
        }
        if (debt.getValor() == null || debt.getValor().doubleValue() <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }
        return repository.save(debt);
    }

    public List<Debt> listar() {
        return repository.findAll();
    }
    
    public Optional<Debt> buscarPorId(Long id) {
        return repository.findById(id);
    }
    
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Dívida não encontrada");
        }
        repository.deleteById(id);
    }
    
    public Debt marcarComoPaga(Long id) {
        Debt debt = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Dívida não encontrada"));
        debt.setPaga(true);
        return repository.save(debt);
    }
}