package com.proyecto.farmacia.DTOs.Proveedor;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProveedorUpdateDTO {

    private String nombre;

    private String telefono;

    private String email;

    private List<Integer> medicamentosId;

}
