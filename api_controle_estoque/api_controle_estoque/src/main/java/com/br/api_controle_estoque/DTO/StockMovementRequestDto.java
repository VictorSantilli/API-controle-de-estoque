package com.br.api_controle_estoque.DTO;

import com.br.api_controle_estoque.model.MovementType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record StockMovementRequestDto(
        @NotNull(message = "O ID do produto é obrigatório")
        Long productId,

        @NotNull(message = "O tipo de movimento é obrigatório")
        MovementType movementType, // Usando enum aqui

        @Positive(message = "A quantidade deve ser um valor positivo")
        @NotNull(message = "A quantidade é obrigatória")
        Integer quantity,

        String observation
) {
}
