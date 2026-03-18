package com.proyecto.farmacia.DTOs.Usuarios;

import com.proyecto.farmacia.util.RolUsuario;

import java.util.List;

public record UsuarioGetDTO(
        Integer id,
        String nombre,
        String email,
        String dni,
        RolUsuario rol,
        Boolean activo,
        List<UsuarioVentaDTO> ventas) {


}
