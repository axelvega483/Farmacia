package com.proyecto.farmacia.DTOs.RecetaMedica;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecetaMedicaPostDTO {

    @NotNull
    private LocalDate fecha;

    @NotNull
    private String medico;

    @NotNull
    private Integer clienteId;

    @NotNull
    private List<Integer> medicamentoIds;

    @NotNull
    private LocalDate vigenteHasta;
}
