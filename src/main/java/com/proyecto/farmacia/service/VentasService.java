package com.proyecto.farmacia.service;

import com.proyecto.farmacia.DTOs.Ventas.*;
import com.proyecto.farmacia.entity.*;
import com.proyecto.farmacia.interfaz.VentaInterfaz;
import com.proyecto.farmacia.repository.ClienteRepository;
import com.proyecto.farmacia.repository.EmpleadoRepository;
import com.proyecto.farmacia.repository.MedicamentoRepository;
import com.proyecto.farmacia.repository.VentaRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.proyecto.farmacia.util.ApiResponse;
import com.proyecto.farmacia.util.EstadoVenta;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
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
    private EmpleadoRepository empleadoRepo;

    @Override
    @Transactional
    public VentaGetDTO create(VentaPostDTO post) {
        Cliente cliente = clienteRepo.findById(post.getClienteId()).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Empleado empleado = empleadoRepo.findById(post.getEmpleadoId()).orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        List<DetalleVenta> detallesFinales = new ArrayList<>();

        for (VentaDetalleDTO detalleDTO : post.getDetalles()) {
            Medicamento medicamento = medicamentoRepo.findById(detalleDTO.getMedicamentoId()).orElseThrow(() -> new RuntimeException("Medicamento no encontrado con ID: " + detalleDTO.getMedicamentoId()));

            if (!medicamento.getActivo()) {
                throw new EntityExistsException("Medicamento inactivo");
            }
            if (medicamento.getStock() < detalleDTO.getCantidad()) {
                throw new EntityExistsException("Stock insuficiente para: " + medicamento.getNombre());
            }

            medicamento.setStock(medicamento.getStock() - detalleDTO.getCantidad());
            medicamentoRepo.save(medicamento);


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
        Venta venta = repo.findById(id).orElse(null);

        if (venta.getEstado().equals(EstadoVenta.ANULADA)) {
            throw new EntityExistsException("La venta ya está anulada");
        }

        for (DetalleVenta d : venta.getDetalleventas()) {
            Medicamento m = d.getMedicamento();
            m.setStock(m.getStock() + d.getCantidad());
            medicamentoRepo.save(m);
        }

        venta.setEstado(EstadoVenta.ANULADA);
        venta.setActivo(false);
        Venta saved = repo.save(venta);
        return mapper.toDTO(saved);
    }

    @Override
    public Optional<VentaGetDTO> findById(Integer id) {
        Optional<Venta> venta = repo.findById(id).filter(Venta::getActivo);
        if (venta.isPresent()) {
            VentaGetDTO dto = mapper.toDTO(venta.get());
            return Optional.of(dto);
        }

        return Optional.empty();
    }

    @Override
    public List<VentaGetDTO> findAll() {
        List<Venta> ventas = repo.findAll();
        List<VentaGetDTO> dtos = new ArrayList<>();
        for (Venta venta : ventas) {
            VentaGetDTO dto = mapper.toDTO(venta);
            dtos.add(dto);
        }
        return dtos;
    }
}
