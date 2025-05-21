package com.proyecto.farmacia.DTOs.Empleados;

import com.proyecto.farmacia.entity.Empleado;
import java.util.List;

public class EmpleadoMapper {

    public static EmpleadoGetDTO toDTO(Empleado empleado) {
        EmpleadoGetDTO dto = new EmpleadoGetDTO();
        dto.setNombre(empleado.getNombre());
        dto.setDni(empleado.getDni());
        dto.setEmail(empleado.getEmail());
        dto.setRol(empleado.getRol());
        dto.setActivo(empleado.getActivo());
        dto.setId(empleado.getId());

        List<EmpleadoVentaDTO> ventas = empleado.getVentas().stream()
                .map(venta -> new EmpleadoVentaDTO(
                venta.getId(),
                venta.getFecha(),
                venta.getTotal())).toList();
        dto.setVentas(ventas);
        return dto;

    }
}
