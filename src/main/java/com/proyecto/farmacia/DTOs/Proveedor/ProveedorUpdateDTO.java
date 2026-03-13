package com.proyecto.farmacia.DTOs.Proveedor;


public record ProveedorUpdateDTO(
         String nombre,
         String telefono,
         String email,
         Boolean activo) {

}
