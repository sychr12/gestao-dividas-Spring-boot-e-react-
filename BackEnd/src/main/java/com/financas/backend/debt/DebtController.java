package com.financas.backend.debt;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/debts")
public class DebtController {

    private final DebtService service;

    public DebtController(DebtService service){
        this.service = service;
    }

    @PostMapping
    public Debt criar(@RequestBody Debt debt){
        return service.salvar(debt);
    }

    @GetMapping
    public List<Debt> listar(){
        return service.listar();
    }
    
}
