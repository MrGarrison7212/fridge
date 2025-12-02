package com.bojan.fridge.controller;

import com.bojan.fridge.domain.dto.FridgeItemCreateRequest;
import com.bojan.fridge.domain.dto.FridgeItemDto;
import com.bojan.fridge.domain.dto.FridgeItemUpdateRequest;
import com.bojan.fridge.service.FridgeItemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/items")
public class FridgeItemController {

    private final FridgeItemService fridgeItemService;

    public FridgeItemController(FridgeItemService fridgeItemService) {
        this.fridgeItemService = fridgeItemService;
    }

    @GetMapping
    public List<FridgeItemDto> getAll() {
        return fridgeItemService.findAll();
    }

    @GetMapping("/{id}")
    public FridgeItemDto getOne(@PathVariable Long id) {
        return fridgeItemService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FridgeItemDto create(@RequestBody @Valid FridgeItemCreateRequest request) {
        return fridgeItemService.create(request);
    }

    @PutMapping("/{id}")
    public FridgeItemDto update(@PathVariable Long id, @RequestBody @Valid FridgeItemUpdateRequest request) {
        return fridgeItemService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        fridgeItemService.delete(id);
    }
}