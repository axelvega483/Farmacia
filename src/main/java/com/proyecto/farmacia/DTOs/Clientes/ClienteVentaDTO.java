package com.proyecto.farmacia.DTOs.Clientes;

import java.time.LocalDate;
public record ClienteVentaDTO(
         Integer id,
         LocalDate fecha,
         Double total) {


}
