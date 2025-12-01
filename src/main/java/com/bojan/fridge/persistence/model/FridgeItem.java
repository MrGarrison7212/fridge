package com.bojan.fridge.persistence.model;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "fridge_item")
public class FridgeItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private FridgeCategory category;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, length = 16)
    private String unit;

    @Column(nullable = false)
    private LocalDate storedAt;

    @Column(nullable = false)
    private LocalDate bestBefore;

    @Column(length = 1000)
    private String notes;

    public FridgeItem(){}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public FridgeCategory getCategory() { return category; }
    public void setCategory(FridgeCategory category) { this.category = category; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit;}
    public LocalDate getStoredAt() { return storedAt; }
    public void setStoredAt(LocalDate storedAt) { this.storedAt = storedAt; }
    public LocalDate getBestBefore() { return bestBefore; }
    public void setBestBefore(LocalDate bestBefore) { this.bestBefore = bestBefore; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    @PrePersist
    void prePersist() {
        if (this.storedAt == null) {
            this.storedAt = LocalDate.now();
        }
    }
}
