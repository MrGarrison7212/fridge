package com.bojan.fridge.domain.dto;

public record AuthRequest(
        String username,
        String password
) {
}
