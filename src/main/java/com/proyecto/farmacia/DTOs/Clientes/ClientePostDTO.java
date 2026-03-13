package com.proyecto.farmacia.DTOs.Clientes;

import jakarta.validation.constraints.NotNull;

public record ClientePostDTO(
        @NotNull
        String nombre,
        @NotNull
        String email,
        @NotNull
        String dni) {


}
