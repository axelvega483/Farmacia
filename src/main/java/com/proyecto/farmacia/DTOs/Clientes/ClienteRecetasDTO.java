package com.proyecto.farmacia.DTOs.Clientes;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRecetasDTO {

    private Integer id;
    private LocalDate fecha;
    private String medico;
    private LocalDate vigenteHasta;
}
