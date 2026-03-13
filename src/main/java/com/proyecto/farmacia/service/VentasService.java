package com.proyecto.farmacia.service;

import com.proyecto.farmacia.DTOs.Ventas.*;
import com.proyecto.farmacia.entity.*;
import com.proyecto.farmacia.interfaz.VentaInterfaz;
import com.proyecto.farmacia.repository.ClienteRepository;
import com.proyecto.farmacia.repository.UsuarioRepository;
import com.proyecto.farmacia.repository.MedicamentoRepository;
import com.proyecto.farmacia.repository.VentaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.proyecto.farmacia.util.EstadoVenta;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class VentasService implements VentaInterfaz {

    @Autowired
    private VentaRepository repo;
    @Autowired
    private VentaMapper mapper;
    @Autowired
    private ClienteRepository clienteRepo;
    @Autowired
    private MedicamentoRepository medicamentoRepo;
    @Autowired
    private UsuarioRepository empleadoRepo;

    @Override
    public VentaGetDTO create(VentaPostDTO post) {
        Cliente cliente = clienteRepo.findById(post.clienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        Usuario empleado = empleadoRepo.findById(post.empleadoId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        List<DetalleVenta> detallesFinales = new ArrayList<>();
        for (VentaDetalleDTO detalleDTO : post.detalles()) {
            Medicamento medicamento = medicamentoRepo.findById(detalleDTO.medicamentoId())
                    .orElseThrow(() ->
                            new EntityNotFoundException(
                                    "Medicamento no encontrado con ID: " + detalleDTO.medicamentoId()
                            ));
            if (!medicamento.isActivo()) {
                throw new IllegalStateException("Medicamento inactivo: " + medicamento.getNombre());
            }
            if (medicamento.getStock() < detalleDTO.cantidad()) {
                throw new IllegalStateException("Stock insuficiente para: " + medicamento.getNombre());
            }
            medicamento.setStock(
                    medicamento.getStock() - detalleDTO.cantidad()
            );
            DetalleVenta detalle = mapper.toDetalleVenta(detalleDTO, medicamento);
            detallesFinales.add(detalle);
        }
        Venta venta = mapper.toEntity(post, cliente, empleado, detallesFinales);
        for (DetalleVenta detalle : detallesFinales) {
            detalle.setVenta(venta);
        }
        Venta savedVenta = repo.save(venta);
        return mapper.toDTO(savedVenta);
    }


    @Override
    public VentaGetDTO cancel(Integer id) {
        Venta venta = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Venta no encontrada"));
        if (venta.getEstado() == EstadoVenta.ANULADA) {
            throw new IllegalStateException("La venta ya está anulada");
        }
        for (DetalleVenta detalle : venta.getDetalleventas()) {
            Medicamento medicamento = detalle.getMedicamento();
            medicamento.setStock(
                    medicamento.getStock() + detalle.getCantidad()
            );
        }
        venta.setEstado(EstadoVenta.ANULADA);
        venta.setActivo(false);
        Venta saved = repo.save(venta);
        return mapper.toDTO(saved);
    }

    @Override
    public Optional<VentaGetDTO> findById(Integer id) {
        return repo.findById(id)
                .filter(Venta::isActivo)
                .map(mapper::toDTO);
    }

    @Override
    public List<VentaGetDTO> findAll() {
        return mapper.toDTOList(repo.findAll());
    }
}
