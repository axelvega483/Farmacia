package com.proyecto.farmacia.DTOs.Medicamentos;

import com.proyecto.farmacia.entity.Medicamento;
import java.util.List;

public class MedicamentoMapper {

    public static MedicamentosGetDTO toDTO(Medicamento medicamento) {
        MedicamentosGetDTO dto = new MedicamentosGetDTO();
        dto.setActivo(medicamento.getActivo());
        dto.setDescripcion(medicamento.getDescripcion());
        dto.setFechaVencimiento(medicamento.getFechaVencimiento());
        dto.setId(medicamento.getId());
        dto.setNombre(medicamento.getNombre());
        dto.setPrecio(medicamento.getPrecio());
        dto.setProveedor(medicamento.getProveedor());
        dto.setRecetaRequerida(medicamento.getRecetaRequerida());
        dto.setStock(medicamento.getStock());
        
        List<MedicamentoDetalleDTO> detalle = medicamento.getDetalleVentas()
                .stream()
                .map(detalles -> new MedicamentoDetalleDTO(
                detalles.getId(),
                detalles.getCantidad(),
                detalles.getPrecioUnitario())).toList();
        dto.setDetalleVentasID(detalle);
        return dto;
    }
}
