package com.br.api_controle_estoque.controller;

import com.br.api_controle_estoque.DTO.Request.InvoiceItemRequest;
import com.br.api_controle_estoque.DTO.Response.InvoiceItemResponseDto;
import com.br.api_controle_estoque.model.InvoiceItem;
import com.br.api_controle_estoque.service.InvoiceItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoice-items")
public class InvoiceItemController {

    @Autowired
    private InvoiceItemService invoiceItemService;

    @PostMapping
    public ResponseEntity<InvoiceItemResponseDto> createInvoiceItem(
            @RequestBody @Valid InvoiceItemRequest dto) {
        InvoiceItem invoiceItem = invoiceItemService.createInvoiceItem(dto);
        return ResponseEntity.status(HttpStatus
                        .CREATED)
                .body(new InvoiceItemResponseDto(
                        invoiceItem.getId(),
                        invoiceItem.getProduct().getName(),
                        invoiceItem.getQuantity(),
                        invoiceItem.getUnitPrice()
                ));
    }

    @GetMapping
    public ResponseEntity<List<InvoiceItemResponseDto>> getAllInvoiceItems() {
        return ResponseEntity.ok(invoiceItemService.getAllInvoiceItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceItemResponseDto> getInvoiceItemById(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceItemService.getInvoiceItemById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceItemResponseDto> updateInvoiceItem(@PathVariable Long id, @RequestBody @Valid InvoiceItemRequest dto) {
        InvoiceItem updatedItem = invoiceItemService.updateInvoiceItem(id, dto);
        return ResponseEntity.ok(new InvoiceItemResponseDto(
                updatedItem.getId(),
                updatedItem.getProduct().getName(),
                updatedItem.getQuantity(),
                updatedItem.getUnitPrice()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoiceItem(@PathVariable Long id) {
        invoiceItemService.deleteInvoiceItem(id);
        return ResponseEntity.noContent().build();
    }
}
