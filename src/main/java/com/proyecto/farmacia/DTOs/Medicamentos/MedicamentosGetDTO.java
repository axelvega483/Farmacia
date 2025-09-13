package com.proyecto.farmacia.DTOs.Medicamentos;

import com.proyecto.farmacia.DTOs.Proveedor.ProveedorGetDTO;
import com.proyecto.farmacia.entity.Proveedor;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicamentosGetDTO {

    private Integer id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private LocalDate fechaVencimiento;
    private Boolean recetaRequerida;
    private Boolean activo;
    private ProveedorGetDTO proveedor;
     private List<MedicamentoDetalleDTO> detalleVentasID;
}
