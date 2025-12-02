package com.bojan.fridge.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record FridgeItemUpdateRequest(
        @NotBlank
        String name,
        @NotBlank
        String category,
        @Min(1)
        int quantity,
        @NotBlank
        String unit,
        @NotNull
        LocalDate storedAt,
        @NotNull
        LocalDate bestBefore,
        String notes
) {}
