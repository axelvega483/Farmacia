package com.proyecto.farmacia.DTOs.Usuarios;

import java.time.LocalDate;

public record UsuarioVentaDTO(
        Integer id,
        LocalDate fecha,
        Double total) {


}
