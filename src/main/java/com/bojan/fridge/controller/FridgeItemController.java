package com.bojan.fridge.controller;

import com.bojan.fridge.domain.dto.FridgeItemCreateRequest;
import com.bojan.fridge.domain.dto.FridgeItemDto;
import com.bojan.fridge.domain.dto.FridgeItemUpdateRequest;
import com.bojan.fridge.service.FridgeItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/items")
@RequiredArgsConstructor
public class FridgeItemController {

    private final FridgeItemService fridgeItemService;

    @GetMapping
    public List<FridgeItemDto> getAll() {
        return fridgeItemService.findAll();
    }

    @GetMapping("/page")
    public Page<FridgeItemDto> getPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return fridgeItemService.findAll(pageable);
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