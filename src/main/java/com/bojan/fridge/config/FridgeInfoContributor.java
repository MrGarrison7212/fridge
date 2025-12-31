package com.bojan.fridge.config;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FridgeInfoContributor implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("app", Map.of(
                        "name", "Fridge Backend",
                        "description", "REST API - fridge",
                        "author", "Bojan Radic",
                        "stack", "Spring Boot, H2, Spring Security, Springdoc (Swagger)"
                ))
                .withDetail("profile", Map.of(
                        "active", "h2",
                        "database", "in-memory H2 (fridgedb)"
                ));
    }
}
