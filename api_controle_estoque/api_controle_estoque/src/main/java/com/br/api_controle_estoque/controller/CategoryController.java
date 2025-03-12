package com.br.api_controle_estoque.controller;

import com.br.api_controle_estoque.DTO.CategoryRequestDto;
import com.br.api_controle_estoque.DTO.CategoryResponseDTO;
import com.br.api_controle_estoque.model.Category;
import com.br.api_controle_estoque.service.CategoryService;
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
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Cadastrar uma nova categoria", description = "Cria uma nova categoria no sistema.")
    @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso")
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(
            @Valid @RequestBody CategoryRequestDto requestDto){

        Category category = new Category();
        category.setName(requestDto.name());
        category.setDescription(requestDto.description());
        Category savedCategory = categoryService.saveCategory(category);
        CategoryResponseDTO responseDTO = new CategoryResponseDTO(savedCategory);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }


    @GetMapping("/list")
    public List<CategoryResponseDTO> listCategory(){
        return categoryService.listCategory().stream() //Percorre elementos na lista
                .map(CategoryResponseDTO::fromEntity) //Converte cada Category para CategoryResponseDTO
                .collect(Collectors.toList());        // Coleta os DTOs em uma lista
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> searchCategory(@PathVariable Long id){
        Category findCategory = categoryService.searchCategory(id);
        if (findCategory == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(CategoryResponseDTO.fromEntity(findCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id,
                                                   @Valid @RequestBody CategoryRequestDto categoryDto){
        Category existingCategory = categoryService.searchCategory(id);

        if (existingCategory != null){
            existingCategory.setName(categoryDto.name());
            existingCategory.setDescription(categoryDto.description());

            Category updateCategory = categoryService.saveCategory(existingCategory);
            return ResponseEntity.ok(new CategoryResponseDTO(updateCategory));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        Category existingCategory = categoryService.searchCategory(id);
        if (existingCategory != null){
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
