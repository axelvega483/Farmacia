package com.proyecto.farmacia.DTOs.RecetaMedica;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecetaMedicaUptadeDTO {

    private LocalDate fecha;

    private String medico;

    private Integer clienteId;

    private List<Integer> medicamentoIds;

    private LocalDate vigenteHasta;

    private Boolean activo;
}
