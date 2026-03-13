package com.proyecto.farmacia.DTOs.Usuarios;

import com.proyecto.farmacia.util.RolEmpleado;

import java.util.List;

public record UsuarioGetDTO(
        Integer id,
        String nombre,
        String email,
        String dni,
        RolEmpleado rol,
        Boolean activo,
        List<UsuarioVentaDTO> ventas) {


}
