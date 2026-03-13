package com.proyecto.farmacia.DTOs.Usuarios;

import com.proyecto.farmacia.util.RolEmpleado;

import java.util.List;

public record UsuarioUpdateDTO(
        String nombre,
        String email,
        String password,
        List<Integer> ventasID,
        RolEmpleado rol,
        String dni,
        Boolean activo) {

}
