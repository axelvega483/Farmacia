package com.proyecto.farmacia.DTOs.Medicamentos;

import com.proyecto.farmacia.DTOs.Proveedor.ProveedorGetDTO;
import com.proyecto.farmacia.DTOs.Proveedor.ProveedorMapper;
import com.proyecto.farmacia.entity.Medicamento;
import com.proyecto.farmacia.entity.Proveedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class MedicamentoMapper {
    @Autowired
    private ProveedorMapper proveedorMapper;

    public MedicamentosGetDTO toDTO(Medicamento medicamento) {
        MedicamentosGetDTO dto = new MedicamentosGetDTO();
        dto.setActivo(medicamento.getActivo());
        dto.setDescripcion(medicamento.getDescripcion());
        dto.setFechaVencimiento(medicamento.getFechaVencimiento());
        dto.setId(medicamento.getId());
        dto.setNombre(medicamento.getNombre());
        dto.setPrecio(medicamento.getPrecio());
        if (medicamento.getProveedor() != null) {
            dto.setProveedor(proveedorMapper.toDTO(medicamento.getProveedor()));
        }
        dto.setRecetaRequerida(medicamento.getRecetaRequerida());
        dto.setStock(medicamento.getStock());

        List<MedicamentoDetalleDTO> detalle = medicamento.getDetalleVentas()
                .stream()
                .map(detalles -> new MedicamentoDetalleDTO(
                        detalles.getId(),
                        detalles.getCantidad(),
                        detalles.getPrecioUnitario())).toList();
        dto.setDetalleVentasID(detalle);
        return dto;
    }

    public Medicamento create(MedicamentoPostDTO post) {
        Medicamento medicamento = new Medicamento();
        medicamento.setActivo(Boolean.TRUE);
        medicamento.setDescripcion(post.getDescripcion());
        medicamento.setFechaVencimiento(post.getFechaVencimiento());
        medicamento.setNombre(post.getNombre());
        medicamento.setPrecio(post.getPrecio());
        medicamento.setProveedor(post.getProveedor());
        medicamento.setRecetaRequerida(post.getRecetaRequerida());
        medicamento.setStock(post.getStock());
        medicamento.setDetalleVentas(Collections.emptyList());
        return medicamento;
    }

    public Medicamento update(Medicamento medicamento, MedicamentoUpdateDTO put,Proveedor proveedor) {
        if (put.getActivo() != null) medicamento.setActivo(put.getActivo());
        if (put.getDescripcion() != null) medicamento.setDescripcion(put.getDescripcion());
        if (put.getFechaVencimiento() != null) medicamento.setFechaVencimiento(put.getFechaVencimiento());
        if (put.getNombre() != null) medicamento.setNombre(put.getNombre());
        if (put.getPrecio() != null) medicamento.setPrecio(put.getPrecio());
        if (put.getProveedor() != null) medicamento.setProveedor(proveedor);
        if (put.getRecetaRequerida() != null) medicamento.setRecetaRequerida(put.getRecetaRequerida());
        if (put.getStock() != null) medicamento.setStock(put.getStock());
        return medicamento;
    }
}
