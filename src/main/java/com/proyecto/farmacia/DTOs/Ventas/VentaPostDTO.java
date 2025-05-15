package com.proyecto.farmacia.DTOs.Ventas;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VentaPostDTO {

    @NotNull
    private Integer id;
    @NotNull
    private LocalDateTime fecha;
    @NotNull
    private Double total;
    @NotNull
    private List<VentaDetalleDTO> detalles;
    @NotNull
    private Integer clienteId;
    @NotNull
    private Integer empleadoId;
    @NotNull
    private Boolean activo;
}
