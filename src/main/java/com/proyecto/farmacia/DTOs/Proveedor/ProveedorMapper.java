package com.proyecto.farmacia.DTOs.Proveedor;

import com.proyecto.farmacia.entity.Medicamento;
import com.proyecto.farmacia.entity.Proveedor;
import java.util.List;
import java.util.stream.Collectors;

public class ProveedorMapper {

    public static ProveedorGetDTO toDTO(Proveedor proveedor) {

        ProveedorGetDTO dto = new ProveedorGetDTO();
        dto.setActivo(proveedor.getActivo());
        dto.setEmail(proveedor.getEmail());
        dto.setId(proveedor.getId());
        dto.setNombre(proveedor.getNombre());
        dto.setTelefono(proveedor.getTelefono());

        List<String> medicamentos = proveedor.getMedicamentos().stream()
                .map(Medicamento::getNombre)
                .collect(Collectors.toList());

        dto.setMedicamentos(medicamentos);

        return dto;
    }
}
