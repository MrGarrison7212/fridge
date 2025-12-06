package com.bojan.fridge.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RootController {

    @GetMapping("/")
    public Map<String, String> home() {
        return Map.of(
                "status", "OK",
                "message", "Fridge backend is running",
                "swagger", "/swagger-ui/index.html"
        );
    }
}
