package com.proyecto.farmacia.DTOs.Clientes;

import com.proyecto.farmacia.entity.Cliente;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ClienteMapper {

    public ClientesGetDTO toDTO(Cliente cliente) {
        ClientesGetDTO dto = new ClientesGetDTO();
        dto.setNombre(cliente.getNombre());
        dto.setEmail(cliente.getEmail());
        dto.setId(cliente.getId());
        dto.setActivo(cliente.getActivo());
        dto.setDni(cliente.getDni());

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

    public Cliente create(ClientePostDTO post) {
        Cliente cliente = new Cliente();
        cliente.setActivo(Boolean.TRUE);
        cliente.setDni(post.getDni());
        cliente.setEmail(post.getEmail());
        cliente.setNombre(post.getNombre());
        cliente.setRecetas(Collections.emptyList());
        cliente.setVentas(Collections.emptyList());
        return cliente;
    }

    public Cliente update(Cliente cliente, ClienteUpdateDTO put) {
        if (put.getDni()!= null) cliente.setDni(put.getDni());
        if (put.getEmail() != null) cliente.setEmail(put.getEmail());
        if (put.getNombre() != null) cliente.setNombre(put.getNombre());
        if (put.getActivo() != null) cliente.setActivo(put.getActivo());
        return cliente;
    }
}
