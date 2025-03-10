package com.br.api_controle_estoque.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "fornecedor")
public class Supplier {

    @Id
    @Column(name = "id_fornecedor")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The name of the supplier is required.")
    @Column(name = "nome_fornecedor")
    private String name;

    @Column(unique = true, nullable = false, name = "telefone")
    @Size(max = 20, message = "The phone number must have a maximum of 20 characters.")
    private String phone;
    @Column(unique = true, nullable = false)
    private String email;


    @NotNull
    @Size(max = 20, message = "The CNPJ must have a maximum of 20 characters.")
    private String cnpj;

    @NotNull
    @Size(max = 10, message = "The zip code must have a maximum of 10 characters.")
    private String cep;

    @Column(name = "logradouro")
    @NotNull
    private String public_place;

    @Column(name = "numero")
    @NotNull
    @Size(max = 10, message = "The number must have a maximum of 10 characters.")
    private String number;

    @Column(name = "bairro")
    @NotNull
    private String neighborhood;

    @Column(name = "cidade")
    @NotNull
    private String city;

    @Column(name = "estado")
    @NotNull
    private String state;


    @OneToMany(mappedBy = "supplier")
    private List<StockMovement> movements;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotEmpty(message = "The name of the supplier is required.") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "The name of the supplier is required.") String name) {
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

    public @NotNull @Size(max = 20, message = "The CNPJ must have a maximum of 20 characters.") String getCnpj() {
        return cnpj;
    }

    public void setCnpj(@NotNull @Size(max = 20, message = "The CNPJ must have a maximum of 20 characters.") String cnpj) {
        this.cnpj = cnpj;
    }

    public @NotNull @Size(max = 10, message = "The zip code must have a maximum of 10 characters.") String getCep() {
        return cep;
    }

    public void setCep(@NotNull @Size(max = 10, message = "The zip code must have a maximum of 10 characters.") String cep) {
        this.cep = cep;
    }

    public @NotNull String getPublic_place() {
        return public_place;
    }

    public void setPublic_place(@NotNull String public_place) {
        this.public_place = public_place;
    }

    public @NotNull @Size(max = 10, message = "The number must have a maximum of 10 characters.") String getNumber() {
        return number;
    }

    public void setNumber(@NotNull @Size(max = 10, message = "The number must have a maximum of 10 characters.") String number) {
        this.number = number;
    }

    public @NotNull String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(@NotNull String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public @NotNull String getCity() {
        return city;
    }

    public void setCity(@NotNull String city) {
        this.city = city;
    }

    public @NotNull String getState() {
        return state;
    }

    public void setState(@NotNull String state) {
        this.state = state;
    }

    public List<StockMovement> getMovements() {
        return movements;
    }

    public void setMovements(List<StockMovement> movements) {
        this.movements = movements;
    }
}
