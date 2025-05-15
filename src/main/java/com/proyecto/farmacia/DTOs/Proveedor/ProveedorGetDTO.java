package com.proyecto.farmacia.DTOs.Proveedor;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProveedorGetDTO {

    private Integer id;
    private String nombre;
    private String telefono;
    private String email;
    private Boolean activo;
    private List<String> medicamentos;
}
