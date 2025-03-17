package com.br.api_controle_estoque.controller;

import com.br.api_controle_estoque.DTO.Request.StockOutputRequestDto;
import com.br.api_controle_estoque.DTO.Response.StockOutputResponseDto;
import com.br.api_controle_estoque.model.StockOutput;
import com.br.api_controle_estoque.service.StockOutputService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock-output")
public class StockOutputController {

    @Autowired
    private StockOutputService stockOutputService;

    @PostMapping
    public ResponseEntity<StockOutputResponseDto> createStockOutput(
            @RequestBody @Valid StockOutputRequestDto dto) {

        StockOutput stockOutput = stockOutputService.createStockOutput(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new StockOutputResponseDto(
                stockOutput.getId(),
                stockOutput.getProduct().getName(),
                stockOutput.getProduct().getId(),
                stockOutput.getQuantity(),
                stockOutput.getOutputDate(),
                stockOutput.getObservation()
        ));
    }

    @GetMapping
    public ResponseEntity<List<StockOutputResponseDto>> getAllStockOutputs() {
        return ResponseEntity.ok(stockOutputService.getAllStockOutputs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockOutputResponseDto> getStockOutputById(@PathVariable Long id) {
        return ResponseEntity.ok(stockOutputService.getStockOutputById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockOutputResponseDto> updateStockOutput(@RequestBody @Valid StockOutputRequestDto dto) {
        StockOutput updatedStockOutput = stockOutputService.createStockOutput(dto);
        return ResponseEntity.ok(new StockOutputResponseDto(
                updatedStockOutput.getId(),
                updatedStockOutput.getProduct().getName(),
                updatedStockOutput.getProduct().getId(),
                updatedStockOutput.getQuantity(),
                updatedStockOutput.getOutputDate(),
                updatedStockOutput.getObservation()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStockOutput(@PathVariable Long id) {
        stockOutputService.deleteStockOutput(id);
        return ResponseEntity.noContent().build();
    }
}
