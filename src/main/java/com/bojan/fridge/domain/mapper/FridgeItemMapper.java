package com.bojan.fridge.domain.mapper;

import com.bojan.fridge.domain.dto.FridgeItemCreateRequest;
import com.bojan.fridge.domain.dto.FridgeItemDto;
import com.bojan.fridge.domain.dto.FridgeItemUpdateRequest;
import com.bojan.fridge.persistence.model.FridgeCategory;
import com.bojan.fridge.persistence.model.FridgeItem;

public final class FridgeItemMapper {
    private FridgeItemMapper() {}

    public static FridgeItemDto toDto(FridgeItem item) {
        if (item == null) {
            return null;
        }
        return new FridgeItemDto(
                item.getId(),
                item.getName(),
                item.getCategory() != null ? item.getCategory().name() : null,
                item.getQuantity(),
                item.getUnit(),
                item.getStoredAt(),
                item.getBestBefore(),
                item.getNotes()
        );
    }

    public static FridgeItem fromCreateRequest(FridgeItemCreateRequest request) {
        if (request == null) {
            return null;
        }
        FridgeItem item = new FridgeItem();
        item.setName(request.name());
        item.setCategory(parseCategory(request.category()));
        item.setQuantity(request.quantity());
        item.setUnit(request.unit());
        item.setStoredAt(request.storedAt());
        item.setBestBefore(request.bestBefore());
        item.setNotes(request.notes());
        return item;
    }

    public static void applyUpdate(FridgeItem existing, FridgeItemUpdateRequest request) {
        if (existing == null || request == null) {
            return;
        }
        existing.setName(request.name());
        existing.setCategory(parseCategory(request.category()));
        existing.setQuantity(request.quantity());
        existing.setUnit(request.unit());
        existing.setStoredAt(request.storedAt());
        existing.setBestBefore(request.bestBefore());
        existing.setNotes(request.notes());
    }

    private static FridgeCategory parseCategory(String category) {
        if (category == null) {
            return FridgeCategory.OTHER;
        }
        try {
            return FridgeCategory.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return FridgeCategory.OTHER;
        }
    }
}
