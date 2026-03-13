package com.proyecto.farmacia.DTOs.RecetaMedica;

import java.time.LocalDate;
import java.util.List;

public record RecetaMedicaUptadeDTO(
         LocalDate fecha,
         String medico,
         Integer clienteId,
         List<Integer> medicamentoIds,
         LocalDate vigenteHasta,
         Boolean activo) {
}
