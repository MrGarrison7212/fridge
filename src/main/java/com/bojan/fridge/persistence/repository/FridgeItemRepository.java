package com.bojan.fridge.persistence.repository;

import com.bojan.fridge.persistence.model.FridgeItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FridgeItemRepository extends JpaRepository<FridgeItem, Long> {
}
