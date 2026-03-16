package com.proyecto.farmacia.DTOs.Proveedor;

import com.proyecto.farmacia.entity.Medicamento;
import com.proyecto.farmacia.entity.Proveedor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProveedorMapper {

    public ProveedorGetDTO toDTO(Proveedor proveedor) {
        List<String> medicamentos = proveedor.getMedicamentos() == null
                ? List.of()
                : proveedor.getMedicamentos()
                .stream()
                .filter(Medicamento::isActivo)
                .map(Medicamento::getNombre)
                .toList();
        return new ProveedorGetDTO(
                proveedor.getId(),
                proveedor.getNombre(),
                proveedor.getTelefono(),
                proveedor.getEmail(),
                proveedor.isActivo(),
                medicamentos
        );

    }
    public ProveedorBasicDTO toBasicDTO(Proveedor proveedor) {

        return new ProveedorBasicDTO(
                proveedor.getId(),
                proveedor.getNombre(),
                proveedor.getTelefono(),
                proveedor.getEmail(),
                proveedor.isActivo()
        );

    }


    public Proveedor toEntity(ProveedorPostDTO post) {
        return Proveedor.builder()
                .nombre(post.nombre())
                .telefono(post.telefono())
                .email(post.email())
                .activo(true)
                .medicamentos(Collections.EMPTY_SET)
                .build();
    }


    public void updateEntityFromDTO(Proveedor proveedor, ProveedorUpdateDTO put) {
        if (put.nombre() != null) proveedor.setNombre(put.nombre());
        if (put.email() != null) proveedor.setEmail(put.email());
        if (put.telefono() != null) proveedor.setTelefono(put.telefono());
        if (put.activo() != null) proveedor.setActivo(put.activo());
    }

    public List<ProveedorGetDTO> toDTOList(List<Proveedor> proveedors) {
        return proveedors.stream().filter(Proveedor::isActivo).map(this::toDTO).toList();
    }
}
