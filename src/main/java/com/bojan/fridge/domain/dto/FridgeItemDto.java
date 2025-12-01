package com.bojan.fridge.domain.dto;

import java.time.LocalDate;

public record FridgeItemDto (
        Long id,
        String name,
        String category,
        int quantity,
        String unit,
        LocalDate storedAt,
        LocalDate bestBefore,
        String notes
) {}
