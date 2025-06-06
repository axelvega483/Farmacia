package com.proyecto.farmacia.DTOs.Medicamentos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicamentoDetalleDTO {

    private Integer id;
    private Integer cantidad;
    private Double precioUnitario;
}
