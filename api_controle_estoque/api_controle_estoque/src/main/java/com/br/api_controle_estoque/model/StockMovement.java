package com.br.api_controle_estoque.model;

import com.br.api_controle_estoque.model.Enum.MovementType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacao")
public class StockMovement {

    @Id
    @Column(name = "id_movimentacao")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "The movement type is required")
    @Column(name = "tipo_movimentacao")
    private MovementType movementType;
    @Column(name = "quantidade")
    @Positive(message = "The quantity must be a positive value")
    @NotNull(message = "The quantity is required")
    private Integer quantity;

    @Column(name = "preco")
    private BigDecimal price;


    @Column(name = "data_movimentacao")
    @NotNull
    private LocalDateTime movement_date;
    @Column(name = "observacao")
    private String observation;


    @ManyToOne
    @JoinColumn(name = "id_produto")
    @NotNull
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id_fornecedor")
    private Supplier supplier;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "The movement type is required") MovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(@NotNull(message = "The movement type is required") MovementType movementType) {
        this.movementType = movementType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getMovement_date() {
        return movement_date;
    }

    public void setMovement_date(LocalDateTime movement_date) {
        this.movement_date = movement_date;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
