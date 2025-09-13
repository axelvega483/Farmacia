package com.proyecto.farmacia.DTOs.Clientes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteUpdateDTO {

    private String nombre;

    private String email;

    private String dni;

    private Boolean activo;
}
