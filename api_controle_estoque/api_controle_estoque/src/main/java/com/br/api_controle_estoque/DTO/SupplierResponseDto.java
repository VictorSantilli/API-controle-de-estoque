package com.br.api_controle_estoque.DTO;

import com.br.api_controle_estoque.model.Product;
import com.br.api_controle_estoque.model.StockMovement;
import com.br.api_controle_estoque.model.Supplier;

import java.util.List;
import java.util.stream.Collectors;

public class SupplierResponseDto {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private List<String> productNames;

    public SupplierResponseDto(Long id, String name, String phone, String email, String address, List<String> productNames) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.productNames = productNames;
    }

    public SupplierResponseDto(Supplier supplier) {
        this.id = supplier.getId();
        this.name = supplier.getName();
        this.phone = supplier.getPhone();
        this.email = supplier.getEmail();
        this.address = supplier.getAddress();
        this.productNames = supplier.getMovements() != null ?
                supplier.getMovements().stream()
                        .map(StockMovement::getProduct)
                        .map(product -> product != null ? product.getName() : null)
                        .distinct()
                        .collect(Collectors.toList())
                : null;
    }

    public static SupplierResponseDto fromEntity(Supplier supplier) {
        return new SupplierResponseDto(
                supplier.getId(),
                supplier.getName(),
                supplier.getPhone(),
                supplier.getEmail(),
                supplier.getAddress(),
                supplier.getMovements() != null ?
                        supplier.getMovements().stream()
                                .map(StockMovement::getProduct)
                                .map(product -> product != null ? product.getName() : null)
                                .distinct()
                                .collect(Collectors.toList())
                        : null
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getProductNames() {
        return productNames;
    }

    public void setProductNames(List<String> productNames) {
        this.productNames = productNames;
    }
}
