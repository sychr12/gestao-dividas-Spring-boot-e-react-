package com.financas.backend.card;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.financas.backend.transaction.Transaction;
import com.financas.backend.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do cartão é obrigatório")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Últimos 4 dígitos são obrigatórios")
    @Size(min = 4, max = 4, message = "Deve ter exatamente 4 dígitos")
    @Column(nullable = false, length = 4)
    private String lastFourDigits;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Transaction> transactions;

    @Column(nullable = true)
    private String bank;

    @Column(name = "used_value", nullable = true, precision = 15, scale = 2)
    private BigDecimal usedValue;

    @Column(name = "limit_value", nullable = true, precision = 15, scale = 2)
    private BigDecimal limitValue;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastFourDigits() {
        return lastFourDigits;
    }

    public void setLastFourDigits(String lastFourDigits) {
        this.lastFourDigits = lastFourDigits;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public BigDecimal getUsedValue() {
        return usedValue;
    }

    public void setUsedValue(BigDecimal usedValue) {
        this.usedValue = usedValue;
    }

    public BigDecimal getLimitValue() {
        return limitValue;
    }

    public void setLimitValue(BigDecimal limitValue) {
        this.limitValue = limitValue;
    }
    
    /**
     * Calcula a porcentagem de uso do limite
     */
    public double getUsagePercentage() {
        if (limitValue == null || limitValue.compareTo(BigDecimal.ZERO) == 0) {
            return 0.0;
        }
        if (usedValue == null) {
            return 0.0;
        }
        return usedValue.divide(limitValue, 4, java.math.RoundingMode.HALF_UP)
                       .multiply(new BigDecimal("100"))
                       .doubleValue();
    }
    
    /**
     * Calcula o limite disponível
     */
    public BigDecimal getAvailableLimit() {
        if (limitValue == null) {
            return BigDecimal.ZERO;
        }
        if (usedValue == null) {
            return limitValue;
        }
        return limitValue.subtract(usedValue);
    }
}