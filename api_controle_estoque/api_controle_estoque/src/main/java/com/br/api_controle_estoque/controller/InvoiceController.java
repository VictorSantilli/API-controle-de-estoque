package com.br.api_controle_estoque.controller;

import com.br.api_controle_estoque.DTO.Request.InvoiceRequestDto;
import com.br.api_controle_estoque.DTO.Response.InvoiceResponseDto;
import com.br.api_controle_estoque.model.Invoice;
import com.br.api_controle_estoque.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<InvoiceResponseDto> createInvoice(@RequestBody @Valid InvoiceRequestDto request) {
        Invoice invoice = invoiceService.createInvoice(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new InvoiceResponseDto(invoice));
    }

    @GetMapping
    public ResponseEntity<List<InvoiceResponseDto>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponseDto> getInvoiceById(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceResponseDto> updateInvoice(@PathVariable Long id, @RequestBody @Valid InvoiceRequestDto dto) {
        Invoice invoice = invoiceService.updateInvoice(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new InvoiceResponseDto(invoice));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }
}
