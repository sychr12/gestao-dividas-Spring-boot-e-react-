package com.financas.backend.user.dto;

public record UserRequest(
        String username,
        String email,
        String senha
) {}
