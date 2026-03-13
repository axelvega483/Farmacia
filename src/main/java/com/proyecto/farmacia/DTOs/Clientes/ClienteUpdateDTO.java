package com.proyecto.farmacia.DTOs.Clientes;

public record ClienteUpdateDTO(
         String nombre,
         String email,
         String dni,
         Boolean activo) {


}
