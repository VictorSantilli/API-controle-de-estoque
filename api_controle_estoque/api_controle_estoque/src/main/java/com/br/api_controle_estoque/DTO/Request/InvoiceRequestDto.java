package com.br.api_controle_estoque.DTO.Request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record InvoiceRequestDto(
        String invoiceNumber,
        Long supplierId,
        LocalDateTime issueDate,
        BigDecimal totalAmount,
        List<Long> invoiceItemIds
) {
}
