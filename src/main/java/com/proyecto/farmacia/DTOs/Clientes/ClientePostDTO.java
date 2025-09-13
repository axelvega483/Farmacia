package com.proyecto.farmacia.DTOs.Clientes;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientePostDTO {

    @NotNull
    private String nombre;
    @NotNull
    private String email;
    @NotNull
    private String dni;
}
