package com.financas.backend.user.dto;

public record LoginRequest(
        String email,
        String password
) {}
