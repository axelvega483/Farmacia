package com.proyecto.farmacia.DTOs.Empleados;

import com.proyecto.farmacia.util.RolEmpleado;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpleadoPostDTO {

    @NotNull
    private Integer id;
    @NotNull
    private String nombre;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private List<Integer> ventasID;
    @NotNull
    private RolEmpleado rol;
    @NotNull
    private String dni;
    @NotNull
    private Boolean activo;
}
