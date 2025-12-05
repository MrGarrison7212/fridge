package com.bojan.fridge.controller;

import com.bojan.fridge.domain.dto.FridgeItemCreateRequest;
import com.bojan.fridge.domain.dto.FridgeItemDto;
import com.bojan.fridge.service.FridgeItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FridgeItemController.class)
@AutoConfigureMockMvc(addFilters = false)
class FridgeItemControllerCreateItemTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    FridgeItemService fridgeItemService;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void postShouldCreateNewFridgeItem() throws Exception {
        FridgeItemCreateRequest request = new FridgeItemCreateRequest(
                "Milk",
                "DAIRY",
                1,
                "pcs",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 5),
                null
        );

        FridgeItemDto response = new FridgeItemDto(
                1L,
                "Milk",
                "DAIRY",
                1,
                "pcs",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 5),
                null
        );

        given(fridgeItemService.create(any())).willReturn(response);

        mockMvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Milk"))
                .andExpect(jsonPath("$.category").value("DAIRY"))
                .andExpect(jsonPath("$.quantity").value(1))
                .andExpect(jsonPath("$.unit").value("pcs"));
    }
}