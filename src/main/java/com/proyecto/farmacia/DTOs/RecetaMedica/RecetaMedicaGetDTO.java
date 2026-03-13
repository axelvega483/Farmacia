package com.proyecto.farmacia.DTOs.RecetaMedica;

import java.time.LocalDate;
import java.util.List;

public record RecetaMedicaGetDTO(
        Integer id,
        String medico,
        LocalDate fecha,
        LocalDate vigenteHasta,
        Boolean activo,
        String clienteNombre,
        List<String> medicamentosNombres) {


}
