package com.br.api_controle_estoque.controller;
import com.br.api_controle_estoque.DTO.SupplierResponseDto;
import com.br.api_controle_estoque.model.Supplier;
import com.br.api_controle_estoque.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @Operation(summary = "Cadastrar um novo fornecedor", description = "Cria um novo fornecedor no sistema.")
    @ApiResponse(responseCode = "201", description = "fornecedor criado com sucesso")
    @PostMapping
    public ResponseEntity<Supplier> createSupplier(@Valid @RequestBody Supplier supplier){
        Supplier savedSupplier = supplierService.saveSupplier(supplier);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Buscar todos os fornecedores", description = "Retorna uma lista com todos os fornecedores cadastrados.")
    @GetMapping("/list")
    public List<SupplierResponseDto> listSupplier(){
        return supplierService.listSupplier().stream()
                .map(SupplierResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Buscar fornecedor por ID", description = "Retorna um fornecedor específico baseado no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Fornecedor encontrado")
    @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponseDto> searchSupplier (@PathVariable Long id){
        Supplier findSupplier = supplierService.searchSupplier(id);

        if( findSupplier == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(SupplierResponseDto.fromEntity(findSupplier));
    }

    @Operation(summary = "Atualizar fornecedor existente", description = "Atualiza as informações de um fornecedor existente.")
    @ApiResponse(responseCode = "200", description = "Fornecedor atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable Long id,
                                                   @Valid @RequestBody Supplier supplier){
        Supplier existingSupplier = supplierService.searchSupplier(id);

        if (existingSupplier != null){
            existingSupplier.setName(supplier.getName());
            existingSupplier.setCnpj(supplier.getCnpj());
            existingSupplier.setCep(supplier.getCep());
            existingSupplier.setPublic_place(supplier.getPublic_place());
            existingSupplier.setNumber(supplier.getNumber());
            existingSupplier.setNeighborhood(supplier.getNeighborhood());
            existingSupplier.setCity(existingSupplier.getCity());
            existingSupplier.setState(supplier.getName());
            existingSupplier.setEmail(supplier.getEmail());
            existingSupplier.setPhone(supplier.getPhone());
            existingSupplier.setMovements(supplier.getMovements());
            return ResponseEntity.ok(supplierService.saveSupplier(existingSupplier));
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Deletar fornecedor", description = "Deleta um fornecedor do sistema com base no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Fornecedor deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id){
        Supplier existingSupplier = supplierService.searchSupplier(id);

        if(existingSupplier != null){
            supplierService.deleteSupplier(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();

    }
}
