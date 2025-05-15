package com.proyecto.farmacia.DTOs.RecetaMedica;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecetaMedicaGetDTO {

    private Integer id;
    private String medico;
    private LocalDate fecha;
    private LocalDate vigenteHasta;
    private Boolean activo;
    private String clienteNombre;
    private List<String> medicamentosNombres;
}
