package com.proyecto.farmacia.DTOs.Empleados;

import com.proyecto.farmacia.util.RolEmpleado;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpleadoUpdateDTO {


    private String nombre;

    private String email;

    private String password;

    private List<Integer> ventasID;

    private RolEmpleado rol;

    private String dni;

    private Boolean activo;
}
