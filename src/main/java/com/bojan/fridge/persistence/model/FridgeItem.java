package com.bojan.fridge.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Table(name = "fridge_item")
@Getter
@Setter
@NoArgsConstructor
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

    @PrePersist
    void prePersist() {
        if (this.storedAt == null) {
            this.storedAt = LocalDate.now();
        }
    }
}
