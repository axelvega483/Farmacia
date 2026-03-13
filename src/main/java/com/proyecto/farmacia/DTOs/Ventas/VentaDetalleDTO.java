package com.proyecto.farmacia.DTOs.Ventas;

import jakarta.validation.constraints.NotNull;

public record VentaDetalleDTO(
        @NotNull
        Integer medicamentoId,
        @NotNull
        Integer cantidad,
        @NotNull
        Double precioUnitario,
        Double subtotal) {


}
