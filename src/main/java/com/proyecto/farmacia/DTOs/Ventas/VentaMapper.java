package com.proyecto.farmacia.DTOs.Ventas;

import com.proyecto.farmacia.entity.Venta;
import java.util.List;

public class VentaMapper {

    public static VentaGetDTO toDTO(Venta venta) {
        VentaGetDTO dto = new VentaGetDTO();
        dto.setActivo(venta.getActivo());
        dto.setCliente(venta.getCliente().getId());
        dto.setEmpleado(venta.getEmpleado().getId());
        dto.setFecha(venta.getFecha());
        dto.setId(venta.getId());
        dto.setTotal(venta.getTotal());
        dto.setEstado(venta.getEstado());

        List<VentaDetalleDTO> detalles = venta.getDetalleventas().stream()
                .map(detalle -> new VentaDetalleDTO(
                detalle.getMedicamento().getId(),
                detalle.getCantidad(),
                detalle.getPrecioUnitario())).toList();
        dto.setDetalleventas(detalles);
        return dto;
    }
}
