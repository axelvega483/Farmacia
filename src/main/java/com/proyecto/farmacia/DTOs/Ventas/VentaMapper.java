package com.proyecto.farmacia.DTOs.Ventas;

import com.proyecto.farmacia.entity.*;
import com.proyecto.farmacia.util.EstadoVenta;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VentaMapper {

    public VentaGetDTO toDTO(Venta venta) {
        List<VentaDetalleDTO> detalles = venta.getDetalleventas().stream()
                .map(detalle -> new VentaDetalleDTO(
                        detalle.getMedicamento().getId(),
                        detalle.getCantidad(),
                        detalle.getPrecioUnitario(),
                        detalle.getCantidad() * detalle.getPrecioUnitario())).toList();

        return new VentaGetDTO(
                venta.getId(),
                venta.getFecha(),
                venta.getTotal(),
                detalles,
                venta.getCliente().getId(),
                venta.getEmpleado().getId(),
                venta.isActivo(),
                venta.getEstado()
        );
    }

    public Venta toEntity(VentaPostDTO post, Cliente cliente, Usuario empleado, List<DetalleVenta> detalles) {
       return Venta.builder()
               .cliente(cliente)
               .empleado(empleado)
               .fecha(post.fecha())
               .detalleventas(detalles)
               .total(calcularTotal(detalles))
               .activo(true)
               .estado(EstadoVenta.FACTURADA)
               .build();
    }



    public DetalleVenta toDetalleVenta(VentaDetalleDTO detalleDTO, Medicamento medicamento) {
        DetalleVenta detalle = new DetalleVenta();
        detalle.setCantidad(detalleDTO.cantidad());
        detalle.setPrecioUnitario(detalleDTO.precioUnitario());
        detalle.setMedicamento(medicamento);
        return detalle;
    }

    private double calcularTotal(List<DetalleVenta> detalles) {
        return detalles.stream()
                .mapToDouble(detalle -> detalle.getCantidad() * detalle.getPrecioUnitario())
                .sum();
    }
    public List<VentaGetDTO> toDTOList(List<Venta> ventas) {
        return ventas.stream().filter(Venta::isActivo).map(this::toDTO).toList();
    }
}
