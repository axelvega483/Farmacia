package com.proyecto.farmacia.DTOs.Clientes;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteUpdateDTO {

    private String nombre;

    private String email;

    private String dni;

    private List<Integer> ventasId;

    private List<Integer> recetasId;
}
