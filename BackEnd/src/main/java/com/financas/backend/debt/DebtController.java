package com.financas.backend.debt;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/debts")
@CrossOrigin(origins = "*")
public class DebtController {

    private final DebtService service;

    public DebtController(DebtService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Debt> criar(@Valid @RequestBody Debt debt) {
        Debt saved = service.salvar(debt);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<Debt>> listar() {
        List<Debt> debts = service.listar();
        return ResponseEntity.ok(debts);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Debt> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/pagar")
    public ResponseEntity<Debt> marcarComoPaga(@PathVariable Long id) {
        Debt debt = service.marcarComoPaga(id);
        return ResponseEntity.ok(debt);
    }
}