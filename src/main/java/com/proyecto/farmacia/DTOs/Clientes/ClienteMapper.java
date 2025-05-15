package com.proyecto.farmacia.DTOs.Clientes;

import com.proyecto.farmacia.entity.Cliente;
import java.util.List;

public class ClienteMapper {

    public static ClientesGetDTO toDTO(Cliente cliente) {
        ClientesGetDTO dto = new ClientesGetDTO();
        dto.setNombre(cliente.getNombre());
        dto.setEmail(cliente.getEmail());
        dto.setId(cliente.getId());
        dto.setActivo(cliente.getActivo());

        List<ClienteVentaDTO> ventas = cliente.getVentas().stream()
                .map(venta -> new ClienteVentaDTO(
                venta.getId(),
                venta.getFecha(),
                venta.getTotal()
        ))
                .toList();
        dto.setVentas(ventas);

        List<ClienteRecetasDTO> recetas = cliente.getRecetas().stream()
                .map(receta -> new ClienteRecetasDTO(
                receta.getId(),
                receta.getFecha(),
                receta.getMedico(),
                receta.getVigenteHasta())).toList();
        
        dto.setRecetas(recetas);
        return dto;

    }
}
