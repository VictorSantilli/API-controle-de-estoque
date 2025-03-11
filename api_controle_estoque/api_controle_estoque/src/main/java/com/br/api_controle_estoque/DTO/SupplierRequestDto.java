package com.br.api_controle_estoque.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

public record SupplierRequestDto(
        @NotEmpty(message = "The name of the supplier is required.")
        String name,

        @Pattern(regexp = "\\d{10,11}", message = "The phone number must contain 10 or 11 numeric digits")
        String phone,
        @Email
        String email,

        @NotNull
        @Pattern(regexp = "\\d{14}", message = "The CNPJ must contain 14 numeric digits")
        String cnpj,

        @NotNull
        @Pattern(regexp = "\\d{8}", message = "The CEP must contain 8 numeric digits")
        String cep,

        @Column(name = "logradouro")
        @NotNull
        String public_place,

        @Column(name = "numero")
        @NotNull
        @Size(max = 10, message = "The number must have a maximum of 10 characters.")
        String number,

        @Column(name = "bairro")
        @NotNull
        String neighborhood,

        @Column(name = "cidade")
        @NotNull
        String city,

        @Column(name = "estado")
        @NotNull
        String state
) {
}
