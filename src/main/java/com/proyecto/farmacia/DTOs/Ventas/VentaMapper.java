package com.proyecto.farmacia.DTOs.Ventas;

import com.proyecto.farmacia.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VentaMapper {

    public VentaGetDTO toDTO(Venta venta) {
        VentaGetDTO dto = new VentaGetDTO();
        dto.setActivo(venta.getActivo());
        dto.setCliente(venta.getCliente().getId());
        dto.setEmpleado(venta.getEmpleado().getId());
        dto.setFecha(venta.getFecha());
        dto.setId(venta.getId());
        dto.setTotal(venta.getTotal());
        dto.setEstado(venta.getEstado());

        List<VentaDetalleDTO> detalles = venta.getDetalleventas().stream()
                .map(detalle -> new VentaDetalleDTO(
                        detalle.getMedicamento().getId(),
                        detalle.getCantidad(),
                        detalle.getPrecioUnitario(),
                        detalle.getCantidad() * detalle.getPrecioUnitario())).toList();
        dto.setDetalleventas(detalles);
        return dto;
    }

    public Venta toEntity(VentaPostDTO post, Cliente cliente, Empleado empleado, List<DetalleVenta> detalles) {
        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setEmpleado(empleado);
        venta.setFecha(post.getFecha());
        venta.setDetalleventas(detalles);
        venta.setTotal(calcularTotal(detalles));
        return venta;
    }

    public DetalleVenta toDetalleVenta(VentaDetalleDTO detalleDTO, Medicamento medicamento) {
        DetalleVenta detalle = new DetalleVenta();
        detalle.setCantidad(detalleDTO.getCantidad());
        detalle.setPrecioUnitario(detalleDTO.getPrecioUnitario());
        detalle.setMedicamento(medicamento);
        return detalle;
    }

    private double calcularTotal(List<DetalleVenta> detalles) {
        return detalles.stream()
                .mapToDouble(detalle -> detalle.getCantidad() * detalle.getPrecioUnitario())
                .sum();
    }
}
