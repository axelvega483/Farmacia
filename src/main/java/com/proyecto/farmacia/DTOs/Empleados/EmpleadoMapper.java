package com.proyecto.farmacia.DTOs.Empleados;

import com.proyecto.farmacia.entity.Empleado;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmpleadoMapper {

    public EmpleadoGetDTO toDTO(Empleado empleado) {
        EmpleadoGetDTO dto = new EmpleadoGetDTO();
        dto.setNombre(empleado.getNombre());
        dto.setDni(empleado.getDni());
        dto.setEmail(empleado.getEmail());
        dto.setRol(empleado.getRol());
        dto.setActivo(empleado.getActivo());
        dto.setId(empleado.getId());
        List<EmpleadoVentaDTO> ventas = Collections.emptyList();
        try {
            if (empleado.getVentas() != null && !empleado.getVentas().isEmpty()) {
                ventas = empleado.getVentas().stream()
                        .map(venta -> new EmpleadoVentaDTO(
                                venta.getId(),
                                venta.getFecha(),
                                venta.getTotal()))
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            ventas = Collections.emptyList();
        }
        dto.setVentas(ventas);
        return dto;

    }

    public Empleado crearte(EmpleadoPostDTO post) {
        Empleado empleado = new Empleado();
        empleado.setActivo(Boolean.TRUE);
        empleado.setDni(post.getDni());
        empleado.setEmail(post.getEmail());
        empleado.setNombre(post.getNombre());
        empleado.setPassword(post.getPassword());
        empleado.setRol(post.getRol());
        empleado.setVentas(Collections.emptyList());
        return empleado;
    }

    public Empleado update(Empleado empleado, EmpleadoUpdateDTO put) {
        if (put.getDni() != null) empleado.setDni(put.getDni());
        if (put.getEmail() != null) empleado.setEmail(put.getEmail());
        if (put.getNombre() != null) empleado.setNombre(put.getNombre());
        if (put.getPassword() != null) empleado.setPassword(put.getPassword());
        if (put.getRol() != null) empleado.setRol(put.getRol());
        if (put.getActivo() != null) empleado.setActivo(put.getActivo());
        return empleado;
    }
}
