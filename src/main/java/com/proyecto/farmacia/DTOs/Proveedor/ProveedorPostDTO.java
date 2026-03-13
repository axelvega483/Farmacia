package com.proyecto.farmacia.DTOs.Proveedor;

import jakarta.validation.constraints.NotNull;
public record ProveedorPostDTO(
        @NotNull
        String nombre,
        @NotNull
        String telefono,
        @NotNull
        String email) {

}
