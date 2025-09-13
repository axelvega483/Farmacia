package com.proyecto.farmacia.DTOs.Medicamentos;

import com.proyecto.farmacia.entity.Proveedor;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicamentoUpdateDTO {

    private String nombre;

    private String descripcion;

    private Double precio;

    private Integer stock;

    private LocalDate fechaVencimiento;

    private Boolean recetaRequerida;

    private Boolean activo;

    private Proveedor proveedor;

}
