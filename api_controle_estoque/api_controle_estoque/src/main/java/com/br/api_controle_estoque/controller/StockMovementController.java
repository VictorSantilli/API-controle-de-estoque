package com.br.api_controle_estoque.controller;

import com.br.api_controle_estoque.DTO.StockMovementRequestDto;
import com.br.api_controle_estoque.DTO.StockMovementRequestUpdateDto;
import com.br.api_controle_estoque.DTO.StockMovementResponseDto;
import com.br.api_controle_estoque.model.StockMovement;
import com.br.api_controle_estoque.service.StockMovementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stockMovement")
public class StockMovementController {

    @Autowired
    private StockMovementService stockMovementService;



    @Operation (summary = "Cadastrar uma nova movimentação", description = "Cria um nova movimentação de estoque no sistema.")
    @ApiResponse(responseCode = "201", description = "Movimentação criada com sucesso.")
    @PostMapping
    public ResponseEntity<StockMovementResponseDto> createStockMovement(
            @Valid @RequestBody StockMovementRequestDto requestDto){

        StockMovement savedStockMovement = stockMovementService.createStockMovement(requestDto);
        StockMovementResponseDto responseDto = new StockMovementResponseDto(savedStockMovement);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @Operation(summary = "Buscar todas as movimentações", description = "Retorna uma lista com todas as movimentações cadastradas.")
    @GetMapping("/list")
    public List<StockMovementResponseDto> listMovements(){
        return stockMovementService.listStockMovement().stream()
                .map(StockMovementResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Buscar movimentação por ID", description = "Retorna uma movimentação específica baseado no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Movimentação encontrada")
    @ApiResponse(responseCode = "404", description = "Movimentação não encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<StockMovementResponseDto> findMovements(@PathVariable Long id){
        StockMovement findMovement = stockMovementService.searchStockMovement(id);

        if (findMovement == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(StockMovementResponseDto.fromEntity(findMovement));
    }

    @Operation(summary = "Atualizar movimentação existente", description = "Atualiza as informações de uma movimentação existente.")
    @ApiResponse(responseCode = "200", description = "Movimentação atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Movimentação não encontrada")
    @PutMapping("/{id}")
    public ResponseEntity<StockMovement> updateMovement(@PathVariable Long id,
                                                 @Valid @RequestBody StockMovementRequestDto movementDto){

        try {
            movementDto = new StockMovementRequestDto(movementDto.productId(),movementDto.movementType(),
                    movementDto.quantity(), movementDto.observation(),
                    movementDto.supplierId(), movementDto.price());
            StockMovement updatedMovement = stockMovementService.updateStockMovement(id,movementDto);
            return ResponseEntity.ok(updatedMovement);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Deletar movimentação", description = "Deleta uma movimentação do sistema com base no ID fornecido.")
    @ApiResponse(responseCode = "204", description = "Movimentação deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Movimentação não encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        StockMovement existingMovement = stockMovementService.searchStockMovement(id);
        if (existingMovement != null){
            stockMovementService.deleteStockMovement(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();

    }
}
