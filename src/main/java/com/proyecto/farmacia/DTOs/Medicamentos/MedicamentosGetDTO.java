package com.proyecto.farmacia.DTOs.Medicamentos;

import com.proyecto.farmacia.DTOs.Proveedor.ProveedorBasicDTO;
import com.proyecto.farmacia.DTOs.Proveedor.ProveedorGetDTO;

import java.time.LocalDate;
import java.util.List;

public record MedicamentosGetDTO(
         Integer id,
         String nombre,
         String descripcion,
         Double precio,
         Integer stock,
         LocalDate fechaVencimiento,
         Boolean recetaRequerida,
         Boolean activo,
         ProveedorBasicDTO proveedor,
         List<MedicamentoDetalleDTO> detalleVentasID) {


}
