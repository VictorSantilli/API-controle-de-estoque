package com.br.api_controle_estoque.DTO;

import com.br.api_controle_estoque.model.Category;
import com.br.api_controle_estoque.model.Enum.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductRequestDto(
        @NotNull(message = "The name of the product is required.")
        String name,
        String description,
        @NotNull
        @Positive
        Integer quantity_min,
        @NotNull
        String unit_of_measure,
        @NotNull
        Status status,
        @NotNull(message = "The category of the product is required")
        Long categoryId
) {
}
