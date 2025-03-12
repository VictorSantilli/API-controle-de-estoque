package com.br.api_controle_estoque.controller;

import com.br.api_controle_estoque.DTO.ProductRequestDto;
import com.br.api_controle_estoque.DTO.ProductResponseDto;
import com.br.api_controle_estoque.model.Category;
import com.br.api_controle_estoque.model.Enum.Status;
import com.br.api_controle_estoque.model.Product;
import com.br.api_controle_estoque.service.CategoryService;
import com.br.api_controle_estoque.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @Autowired
    private CategoryService categoryService;


    @Operation(summary = "Cadastrar um novo produto", description = "Cria um novo produto no sistema.")
    @ApiResponse(responseCode = "201", description = "Produto criado com sucesso")
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto requestDto){
        Category category = categoryService.searchCategory(requestDto.categoryId());

        Product product = new Product();
        product.setName(requestDto.name());
        product.setDescription(requestDto.description());
        product.setQuantity_min(requestDto.quantity_min());
        product.setUnit_of_measure(requestDto.unit_of_measure());
        product.setStatus(requestDto.status());
        product.setCategory(category);

        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductResponseDto.fromEntity(savedProduct));
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
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id,
                                                 @Valid @RequestBody ProductRequestDto requestDto){
        Product existingProduct = productService.searchProduct(id);
        Category category = categoryService.searchCategory(requestDto.categoryId());

        if (existingProduct != null){
            existingProduct.setName(requestDto.name());
            existingProduct.setQuantity_min(requestDto.quantity_min());
            existingProduct.setDescription(requestDto.description());
            existingProduct.setUnit_of_measure(requestDto.unit_of_measure());
            existingProduct.setStatus(requestDto.status());
            existingProduct.setCategory(category);

            Product updatedProduct = productService.saveProduct(existingProduct);
            return ResponseEntity.ok(ProductResponseDto.fromEntity(updatedProduct));
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
