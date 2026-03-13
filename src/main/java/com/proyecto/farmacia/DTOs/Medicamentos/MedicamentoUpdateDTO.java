package com.proyecto.farmacia.DTOs.Medicamentos;

import com.proyecto.farmacia.entity.Proveedor;

import java.time.LocalDate;

public record MedicamentoUpdateDTO(
        String nombre,
        String descripcion,
        Double precio,
        Integer stock,
        LocalDate fechaVencimiento,
        Boolean recetaRequerida,
        Boolean activo,
        Proveedor proveedor) {


}
