package com.proyecto.farmacia.DTOs.Empleados;

import com.proyecto.farmacia.util.RolEmpleado;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpleadoGetDTO {

    private Integer id;
    private String nombre;
    private String email;
    private String dni;
    private RolEmpleado rol;
    private Boolean activo;
    private List<EmpleadoVentaDTO> ventas;
}
