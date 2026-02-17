package com.financas.backend.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransactionRequest(
        @NotBlank String description,
        @NotNull BigDecimal amount,
        @NotNull LocalDate date
) {
}