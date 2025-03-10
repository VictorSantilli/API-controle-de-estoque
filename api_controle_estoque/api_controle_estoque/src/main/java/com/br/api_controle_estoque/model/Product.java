package com.br.api_controle_estoque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "produto")
public class Product {

    @Id
    @Column(name = "id_produto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "The name of the product is required.")
    @Column(name = "nome")
    private String name;
    @Column(name = "descricao")
    private String description;

    @Column(name = "quantidade_estoque")
    @NotNull
    @Positive
    private Integer quantity_stock;

    @Column(name = "quantidade_minima")
    @NotNull
    @Positive
    private Integer quantity_min;

    @Column(name = "unidade_de_medida")
    @NotNull
    private String unit_of_measure;

    @NotNull
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<StockMovement> stockMovements;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public @NotEmpty(message = "The name of the product is required.") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "The name of the product is required.") String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public @NotNull @Positive Integer getQuantity_stock() {
        return quantity_stock;
    }

    public void setQuantity_stock(@NotNull @Positive Integer quantity_stock) {
        this.quantity_stock = quantity_stock;
    }

    public @NotNull @Positive Integer getQuantity_min() {
        return quantity_min;
    }

    public void setQuantity_min(@NotNull @Positive Integer quantity_min) {
        this.quantity_min = quantity_min;
    }

    public @NotNull String getUnit_of_measure() {
        return unit_of_measure;
    }

    public void setUnit_of_measure(@NotNull String unit_of_measure) {
        this.unit_of_measure = unit_of_measure;
    }

    @NotNull
    public boolean isStatus() {
        return status;
    }

    public void setStatus(@NotNull boolean status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<StockMovement> getStockMovements() {
        return stockMovements;
    }

    public void setStockMovements(List<StockMovement> stockMovements) {
        this.stockMovements = stockMovements;
    }

}
