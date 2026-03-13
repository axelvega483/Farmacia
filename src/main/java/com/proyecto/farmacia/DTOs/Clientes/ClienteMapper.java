package com.proyecto.farmacia.DTOs.Clientes;

import com.proyecto.farmacia.entity.Cliente;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ClienteMapper {

    public ClientesGetDTO toDTO(Cliente cliente) {
        List<ClienteVentaDTO> ventas = cliente.getVentas().stream()
                .filter(venta -> venta.isActivo())
                .map(venta -> new ClienteVentaDTO(
                        venta.getId(),
                        venta.getFecha(),
                        venta.getTotal()
                ))
                .toList();

        List<ClienteRecetasDTO> recetas = cliente.getRecetas().stream()
                .map(receta -> new ClienteRecetasDTO(
                        receta.getId(),
                        receta.getFecha(),
                        receta.getMedico(),
                        receta.getVigenteHasta())).toList();

        return new ClientesGetDTO(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getDni(),
                cliente.isActivo(),
                recetas,
                ventas);
    }

    public Cliente toEntity(ClientePostDTO post) {
        return Cliente.builder()
                .dni(post.dni())
                .email(post.email())
                .nombre(post.nombre())
                .recetas(Collections.emptyList())
                .ventas(Collections.emptyList())
                .activo(true)
                .build();

    }

    public Cliente fromUpdateDTO(Cliente cliente, ClienteUpdateDTO put) {
        if (put.dni() != null) cliente.setDni(put.dni());
        if (put.email() != null) cliente.setEmail(put.email());
        if (put.nombre() != null) cliente.setNombre(put.nombre());
        if (put.activo() != null) cliente.setActivo(put.activo());
        return cliente;
    }

    public List<ClientesGetDTO> toDTOList(List<Cliente> clientes) {
        return clientes.stream().filter(Cliente::isActivo).map(this::toDTO).toList();
    }
}
