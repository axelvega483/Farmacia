package com.proyecto.farmacia.DTOs.Ventas;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VentaPostDTO {

    @NotNull
    private LocalDate fecha;
    @NotNull
    private Double total;
    @NotNull
    private List<VentaDetalleDTO> detalles;
    @NotNull
    private Integer clienteId;
    @NotNull
    private Integer empleadoId;
}
