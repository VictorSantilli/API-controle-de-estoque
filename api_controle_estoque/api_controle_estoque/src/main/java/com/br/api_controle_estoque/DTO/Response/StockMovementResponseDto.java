package com.br.api_controle_estoque.DTO.Response;

import com.br.api_controle_estoque.model.StockMovement;

import java.math.BigDecimal;

public class StockMovementResponseDto {
    private Long id;
    private String productName;
    private String movementType;
    private Integer quantity;
    private String movementDate;
    private String observation;
    private String supplierName;
    private BigDecimal price;



    public StockMovementResponseDto(Long id, String productName, String movementType,
                                    Integer quantity, String movementDate,
                                    String observation, String supplierName,
                                    BigDecimal price) {
        this.id = id;
        this.productName = productName;
        this.movementType = movementType;
        this.quantity = quantity;
        this.movementDate = movementDate;
        this.observation = observation;
        this.supplierName = supplierName;
        this.price = price;

    }

    public StockMovementResponseDto(StockMovement stockMovement) {
        this.id = stockMovement.getId();
        this.productName = stockMovement.getProduct() != null ? stockMovement.getProduct().getName() : null;
        this.movementType = stockMovement.getMovementType() != null ? stockMovement.getMovementType().name() : null;
        this.quantity = stockMovement.getQuantity();
        this.movementDate = stockMovement.getMovement_date() != null ? stockMovement.getMovement_date().toString() : null;
        this.observation = stockMovement.getObservation();
        this.supplierName = stockMovement.getSupplier() != null ? stockMovement.getSupplier().getName() : null;
        this.price = stockMovement.getPrice();

    }
    public static StockMovementResponseDto fromEntity(StockMovement stockMovement) {
        return new StockMovementResponseDto(
                stockMovement.getId(),
                stockMovement.getProduct() != null ? stockMovement.getProduct().getName() : null,
                stockMovement.getMovementType() != null ? stockMovement.getMovementType().name() : null,
                stockMovement.getQuantity(),
                stockMovement.getMovement_date() != null ? stockMovement.getMovement_date().toString() : null,
                stockMovement.getObservation(),
                stockMovement.getSupplier() != null ? stockMovement.getSupplier().getName() : null,
                stockMovement.getPrice()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(String movementDate) {
        this.movementDate = movementDate;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
