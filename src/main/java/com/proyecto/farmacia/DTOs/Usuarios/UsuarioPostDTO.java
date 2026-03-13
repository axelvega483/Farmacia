package com.proyecto.farmacia.DTOs.Usuarios;

import com.proyecto.farmacia.util.RolEmpleado;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioPostDTO
        (@NotNull
         String nombre,
         @NotNull
         String email,
         @NotNull
         @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
         String password,
         @NotNull
         RolEmpleado rol,
         @NotNull
         String dni) {


}
