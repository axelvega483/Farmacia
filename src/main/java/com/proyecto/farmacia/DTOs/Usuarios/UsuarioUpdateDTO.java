package com.proyecto.farmacia.DTOs.Usuarios;

import com.proyecto.farmacia.util.RolUsuario;

import java.util.List;

public record UsuarioUpdateDTO(
        String nombre,
        String email,
        String password,
        List<Integer> ventasID,
        RolUsuario rol,
        String dni,
        Boolean activo) {

}
