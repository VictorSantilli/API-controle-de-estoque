package com.br.api_controle_estoque.DTO.Request;

import com.br.api_controle_estoque.model.Enum.MovementType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record StockMovementRequestDto(
        @NotNull(message = "O ID do produto é obrigatório")
        Long productId,

        @Enumerated(EnumType.STRING)
        @NotNull(message = "The movement type is required")
        MovementType movementType, // Usando enum aqui

        @Positive(message = "The quantity must be a positive value")
        @NotNull(message = "The quantity is required")
        Integer quantity,

        String observation,
        Long supplierId,
        BigDecimal price
) {
}
