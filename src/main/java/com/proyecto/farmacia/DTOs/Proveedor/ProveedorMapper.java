package com.proyecto.farmacia.DTOs.Proveedor;

import com.proyecto.farmacia.entity.Medicamento;
import com.proyecto.farmacia.entity.Proveedor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class ProveedorMapper {

    public  ProveedorGetDTO toDTO(Proveedor proveedor) {
        ProveedorGetDTO dto = new ProveedorGetDTO();
        dto.setActivo(proveedor.getActivo());
        dto.setEmail(proveedor.getEmail());
        dto.setId(proveedor.getId());
        dto.setNombre(proveedor.getNombre());
        dto.setTelefono(proveedor.getTelefono());
        List<String> medicamentos = Collections.emptyList();
        try {
            if (proveedor.getMedicamentos() != null) {
                medicamentos = proveedor.getMedicamentos().stream()
                        .map(Medicamento::getNombre)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            medicamentos = Collections.emptyList();
        }
        dto.setMedicamentos(medicamentos);
        return dto;
    }
    public Proveedor create(ProveedorPostDTO post){
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(post.getNombre());
        proveedor.setEmail(post.getEmail());
        proveedor.setTelefono(post.getTelefono());
        proveedor.setActivo(Boolean.TRUE);
        proveedor.setMedicamentos(Collections.emptyList());
        return proveedor;
    }
    public Proveedor update(Proveedor proveedor,ProveedorUpdateDTO put){
        if(put.getNombre()!=null) proveedor.setNombre(put.getNombre());
        if(put.getEmail()!=null) proveedor.setEmail(put.getEmail());
        if(put.getTelefono()!=null)  proveedor.setTelefono(put.getTelefono());
        if(put.getActivo()!=null)  proveedor.setActivo(put.getActivo());
        return proveedor;
    }
}
