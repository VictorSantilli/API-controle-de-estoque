package com.br.api_controle_estoque.DTO;

import com.br.api_controle_estoque.model.Enum.MovementType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record StockMovementRequestUpdateDto(
        @NotNull
        Long idMovement,

        @NotNull
        Long idProduct,

        @Positive(message = "The quantity must be a positive value")
        @NotNull(message = "The quantity is required")
        Integer newQuantity,
        @Enumerated(EnumType.STRING)
        @NotNull(message = "The movement type is required")
        MovementType newMovementType,

        String observation,
        Long supplierId,
        BigDecimal price
) {
}
