package com.proyecto.farmacia.DTOs.Medicamentos;

import com.proyecto.farmacia.entity.Proveedor;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicamentoPostDTO {

    @NotNull
    private Integer id;
    @NotNull
    private String nombre;
    @NotNull
    private String descripcion;
    @NotNull
    private Double precio;
    @NotNull
    private Integer stock;
    @NotNull
    private LocalDate fechaVencimiento;
    @NotNull
    private Boolean recetaRequerida;
    @NotNull
    private Boolean activo;
    @NotNull
    private Proveedor proveedor;
    @NotNull
    private List<Integer> detalleVentasID;
}
