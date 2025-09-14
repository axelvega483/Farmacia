package com.proyecto.farmacia.DTOs.Ventas;

import com.proyecto.farmacia.util.EstadoVenta;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VentaGetDTO {

    private Integer id;
    private LocalDate fecha;
    private Double total;
    private List<VentaDetalleDTO> detalleventas;
    private Integer cliente;
    private Integer empleado;
    private Boolean activo;
    private EstadoVenta estado;
}
