package com.proyecto.farmacia.DTOs.Proveedor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProveedorUpdateDTO {

    private String nombre;

    private String telefono;

    private String email;

    private Boolean activo;
}
