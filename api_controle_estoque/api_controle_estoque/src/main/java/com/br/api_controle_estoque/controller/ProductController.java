package com.br.api_controle_estoque.controller;

import com.br.api_controle_estoque.DTO.ProductResponseDto;
import com.br.api_controle_estoque.model.Product;
import com.br.api_controle_estoque.service.ProductService;
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
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Cadastrar um novo produto", description = "Cria um novo produto no sistema.")
    @ApiResponse(responseCode = "201", description = "Produto criado com sucesso")
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product){
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Buscar todos os produtos", description = "Retorna uma lista com todos os produtos cadastrados.")
    @GetMapping("/list")
    public List<ProductResponseDto> listProducts(){
        return productService.listProduct().
                stream().
                map(ProductResponseDto::fromEntity).
                collect(Collectors.toList());
    }

    @Operation(summary = "Buscar produto por ID", description = "Retorna um produto específico baseado no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Produto encontrado")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findProducts(@PathVariable Long id){
        Product findProduct = productService.searchProduct(id);

        if (findProduct == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ProductResponseDto.fromEntity(findProduct));
    }

    @Operation(summary = "Atualizar produto existente", description = "Atualiza as informações de um produto existente.")
    @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
                                                 @Valid @RequestBody Product product){
        Product existingProduct = productService.searchProduct(id);

        if (existingProduct != null){
            existingProduct.setName(product.getName());
            existingProduct.setQuantity_min(product.getQuantity_min());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setStockMovements(product.getStockMovements());
            return ResponseEntity.ok(productService.saveProduct(existingProduct));
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Deletar produto", description = "Deleta um produto do sistema com base no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        Product findProduct = productService.searchProduct(id);
        if (findProduct != null){
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();

    }
}
