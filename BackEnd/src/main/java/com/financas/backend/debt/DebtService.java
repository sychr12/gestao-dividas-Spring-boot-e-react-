package com.financas.backend.debt;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DebtService {
    
    private final DebtRepository repository;

    public DebtService(DebtRepository repository){
        this.repository = repository;

    }

    public Debt salvar(Debt debt){
        if (debt != null) {
            return repository.save(debt);
        }
        return null;
    }

    public List<Debt> listar(){
        return repository.findAll();
    }
}
