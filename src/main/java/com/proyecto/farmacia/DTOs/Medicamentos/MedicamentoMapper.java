package com.proyecto.farmacia.DTOs.Medicamentos;

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
        List<MedicamentoDetalleDTO> detalle = medicamento.getDetalleVentas()
                .stream().filter(detalleVenta -> detalleVenta.getVenta().isActivo())
                .map(detalles -> new MedicamentoDetalleDTO(
                        detalles.getId(),
                        detalles.getCantidad(),
                        detalles.getPrecioUnitario())).toList();

        return new MedicamentosGetDTO(
                medicamento.getId(),
                medicamento.getNombre(),
                medicamento.getDescripcion(),
                medicamento.getPrecio(),
                medicamento.getStock(),
                medicamento.getFechaVencimiento(),
                medicamento.getRecetaRequerida(),
                medicamento.isActivo(),
                proveedorMapper.toBasicDTO(medicamento.getProveedor()),
                detalle
        );

    }

    public Medicamento toEntity(MedicamentoPostDTO post,Proveedor proveedor) {
        return Medicamento.builder()
                .nombre(post.nombre())
                .descripcion(post.descripcion())
                .precio(post.precio())
                .stock(post.stock())
                .fechaVencimiento(post.fechaVencimiento())
                .recetaRequerida(post.recetaRequerida())
                .activo(true)
                .proveedor(proveedor)
                .detalleVentas(Collections.emptyList())
                .build();
    }

    public void updateEntityFromDTO(Medicamento medicamento, MedicamentoUpdateDTO put, Proveedor proveedor) {
        if (put.activo() != null) medicamento.setActivo(put.activo());
        if (put.descripcion() != null) medicamento.setDescripcion(put.descripcion());
        if (put.fechaVencimiento() != null) medicamento.setFechaVencimiento(put.fechaVencimiento());
        if (put.nombre() != null) medicamento.setNombre(put.nombre());
        if (put.precio() != null) medicamento.setPrecio(put.precio());
        if (put.proveedor() != null) medicamento.setProveedor(proveedor);
        if (put.recetaRequerida() != null) medicamento.setRecetaRequerida(put.recetaRequerida());
        if (put.stock() != null) medicamento.setStock(put.stock());
    }

    public List<MedicamentosGetDTO> toDTOList(List<Medicamento> medicamentos) {
        return medicamentos.stream().filter(Medicamento::isActivo).map(this::toDTO).toList();
    }
}
