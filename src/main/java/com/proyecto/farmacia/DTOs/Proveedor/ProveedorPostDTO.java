package com.proyecto.farmacia.DTOs.Proveedor;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProveedorPostDTO {

    @NotNull
    private String nombre;
    @NotNull
    private String telefono;
    @NotNull
    private String email;
    @NotNull
    private List<Integer> medicamentosId;
}
