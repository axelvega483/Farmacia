package com.proyecto.farmacia.DTOs.Clientes;

import java.time.LocalDate;

public record ClienteRecetasDTO(
         Integer id,
         LocalDate fecha,
         String medico,
         LocalDate vigenteHasta) {


}
