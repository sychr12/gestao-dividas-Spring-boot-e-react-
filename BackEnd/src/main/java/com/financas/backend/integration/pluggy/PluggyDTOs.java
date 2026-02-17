package com.financas.backend.integration.pluggy;

import java.time.LocalDate;
import java.util.List;

/**
 * DTOs usados para mapear respostas da API Pluggy
 * Mantidos juntos para organização
 */
public class PluggyDTOs {

  /* =======================
     CONNECT TOKEN
     ======================= */
  public static class ConnectTokenResponse {
    public String connectToken;
  }

  /* =======================
     CREDIT CARDS
     ======================= */
  public static class CreditCardResponse {
    public List<CreditCard> results;
  }

  public static class CreditCard {
    public String id;
    public String name;
    public String brand;
    public Double creditLimit;
    public Double usedCreditLimit;
    public String status;
  }

  /* =======================
     TRANSACTIONS
     ======================= */
  public static class TransactionResponse {
    public List<Transaction> results;
  }

  public static class Transaction {
    public String id;
    public String description;
    public Double amount;
    public LocalDate date;
    public String creditCardId;
  }
}
