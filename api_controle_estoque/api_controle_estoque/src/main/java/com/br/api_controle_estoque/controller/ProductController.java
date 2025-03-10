package com.br.api_controle_estoque.controller;

import com.br.api_controle_estoque.DTO.ProductResponseDto;
import com.br.api_controle_estoque.model.Product;
import com.br.api_controle_estoque.service.ProductService;
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

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product){
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/list")
    public List<ProductResponseDto> listProducts(){
        return productService.listProduct().
                stream().
                map(ProductResponseDto::fromEntity).
                collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findProducts(@PathVariable Long id){
        Product findProduct = productService.searchProduct(id);

        if (findProduct == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ProductResponseDto.fromEntity(findProduct));
    }

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
