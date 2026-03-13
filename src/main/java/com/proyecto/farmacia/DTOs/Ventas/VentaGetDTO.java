package com.proyecto.farmacia.DTOs.Ventas;

import com.proyecto.farmacia.util.EstadoVenta;

import java.time.LocalDate;
import java.util.List;

public record VentaGetDTO(
        Integer id,
        LocalDate fecha,
        Double total,
        List<VentaDetalleDTO> detalleventas,
        Integer cliente,
        Integer empleado,
        Boolean activo,
        EstadoVenta estado) {

}
