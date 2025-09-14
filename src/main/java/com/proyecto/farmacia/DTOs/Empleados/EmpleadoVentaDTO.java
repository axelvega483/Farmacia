package com.proyecto.farmacia.DTOs.Empleados;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoVentaDTO {

    private Integer id;
    private LocalDate fecha;
    private Double total;
}
