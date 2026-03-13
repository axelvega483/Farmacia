package com.proyecto.farmacia.DTOs.Ventas;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record VentaPostDTO(
        @NotNull
        LocalDate fecha,
        @NotNull
        Double total,
        @NotNull
        List<VentaDetalleDTO> detalles,
        @NotNull
        Integer clienteId,
        @NotNull
        Integer empleadoId) {


}
