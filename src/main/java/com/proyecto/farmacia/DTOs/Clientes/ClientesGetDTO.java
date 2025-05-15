package com.proyecto.farmacia.DTOs.Clientes;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientesGetDTO {

    private Integer id;
    private String nombre;
    private String email;
    private List<ClienteVentaDTO> ventas;
    private String dni;
    private List<ClienteRecetasDTO> recetas;
    private Boolean activo;
}
