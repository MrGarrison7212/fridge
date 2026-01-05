package com.bojan.fridge.service;

import com.bojan.fridge.domain.dto.FridgeItemCreateRequest;
import com.bojan.fridge.domain.dto.FridgeItemDto;
import com.bojan.fridge.domain.dto.FridgeItemUpdateRequest;
import com.bojan.fridge.domain.mapper.FridgeItemMapper;
import com.bojan.fridge.exception.FridgeItemNotFoundException;
import com.bojan.fridge.persistence.model.FridgeItem;
import com.bojan.fridge.persistence.repository.FridgeItemRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FridgeItemService {

    private final FridgeItemRepository repository;

    public List<FridgeItemDto> findAll() {
        return repository.findAll()
                .stream()
                .map(FridgeItemMapper::toDto)
                .toList();
    }

    public Page<FridgeItemDto> findAllPaged(Pageable pageable) {
        return repository.findAll(pageable)
                .map(FridgeItemMapper::toDto);
    }

    public FridgeItemDto findById(Long id) {
        FridgeItem item = repository.findById(id)
                .orElseThrow(() -> new FridgeItemNotFoundException(id));
        return FridgeItemMapper.toDto(item);
    }

    public FridgeItemDto create(FridgeItemCreateRequest request) {
        FridgeItem entity = FridgeItemMapper.fromCreateRequest(request);
        FridgeItem saved = repository.save(entity);
        return FridgeItemMapper.toDto(saved);
    }

    public FridgeItemDto update(Long id, FridgeItemUpdateRequest request) {
        FridgeItem existing = repository.findById(id)
                .orElseThrow(() -> new FridgeItemNotFoundException(id));
        FridgeItemMapper.applyUpdate(existing, request);
        FridgeItem saved = repository.save(existing);
        return FridgeItemMapper.toDto(saved);
    }

    public void delete(Long id) {
        FridgeItem existing = repository.findById(id)
                .orElseThrow(() -> new FridgeItemNotFoundException(id));
        repository.delete(existing);
    }
}
