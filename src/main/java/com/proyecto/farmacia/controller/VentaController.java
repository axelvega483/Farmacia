package com.proyecto.farmacia.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.io.FileInputStream;
import com.proyecto.farmacia.DTOs.Ventas.VentaDetalleDTO;
import com.proyecto.farmacia.DTOs.Ventas.VentaGetDTO;
import com.proyecto.farmacia.DTOs.Ventas.VentaMapper;
import com.proyecto.farmacia.DTOs.Ventas.VentaPostDTO;
import com.proyecto.farmacia.PDF.PdfGenerator;
import com.proyecto.farmacia.entity.Cliente;
import com.proyecto.farmacia.entity.DetalleVenta;
import com.proyecto.farmacia.entity.Empleado;
import com.proyecto.farmacia.entity.Medicamento;
import com.proyecto.farmacia.entity.Venta;
import com.proyecto.farmacia.service.ClienteService;
import com.proyecto.farmacia.service.EmpleadoService;
import com.proyecto.farmacia.service.MedicamentoService;
import com.proyecto.farmacia.service.VentasService;
import com.proyecto.farmacia.util.ApiResponse;
import com.proyecto.farmacia.util.EstadoVenta;
import jakarta.validation.Valid;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("venta")
public class VentaController {

    @Autowired
    private VentasService ventasService;

    @Autowired
    private MedicamentoService medicamentoService;

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            List<VentaGetDTO> dto = ventasService.listar().stream()
                    .map(VentaMapper::toDTO).toList();
            return new ResponseEntity<>(new ApiResponse<>("Ventas", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> obtener(@PathVariable Integer id) {
        try {
            Venta venta = ventasService.obtener(id).orElse(null);
            if (venta != null) {
                VentaGetDTO dto = VentaMapper.toDTO(venta);
                return new ResponseEntity<>(new ApiResponse<>("Venta", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("No se encontro venta", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/factura")
    public ResponseEntity<?> descargarFactura(@PathVariable Integer id) {
        try {
            Venta venta = ventasService.obtener(id)
                    .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

            String rutaPDF = PdfGenerator.generarFacturaPDF(venta);

            File pdfFile = new File(rutaPDF);
            if (!pdfFile.exists()) {
                return new ResponseEntity<>(new ApiResponse<>("Factura no encontrada", null, false), HttpStatus.NOT_FOUND);
            }

            InputStreamResource resource = new InputStreamResource(new FileInputStream(pdfFile));

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + pdfFile.getName());

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(pdfFile.length())
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);

        } catch (FileNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> cargar(@Valid @RequestBody VentaPostDTO ventaDTO) {
        try {
            Cliente cliente = clienteService.obtener(ventaDTO.getClienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
            Empleado empleado = empleadoService.obtener(ventaDTO.getEmpleadoId())
                    .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

            List<DetalleVenta> detallesFinales = new ArrayList<>();
            double totalVenta = 0;

            for (VentaDetalleDTO detalleDTO : ventaDTO.getDetalles()) {
                Medicamento medicamento = medicamentoService.obtener(detalleDTO.getMedicamentoId())
                        .orElseThrow(() -> new RuntimeException("Medicamento no encontrado con ID: " + detalleDTO.getMedicamentoId()));

                if (!medicamento.getActivo()) {
                    return ResponseEntity.badRequest().body(new ApiResponse<>("Medicamento inactivo", null, false));
                }

                if (medicamento.getStock() < detalleDTO.getCantidad()) {
                    return ResponseEntity.badRequest().body(new ApiResponse<>("Stock insuficiente para: " + medicamento.getNombre(), null, false));
                }

                medicamento.setStock(medicamento.getStock() - detalleDTO.getCantidad());
                medicamentoService.guardar(medicamento);

                DetalleVenta detalle = new DetalleVenta();
                detalle.setCantidad(detalleDTO.getCantidad());
                detalle.setPrecioUnitario(detalleDTO.getPrecioUnitario());
                detalle.setMedicamento(medicamento);
                detalle.setVenta(null);

                detallesFinales.add(detalle);
                totalVenta += detalleDTO.getCantidad() * detalleDTO.getPrecioUnitario();
            }

            Venta venta = new Venta();
            venta.setCliente(cliente);
            venta.setEmpleado(empleado);
            venta.setFecha(ventaDTO.getFecha());
            venta.setDetalleventas(detallesFinales);
            venta.setTotal(totalVenta);

            for (DetalleVenta d : detallesFinales) {
                d.setVenta(venta);
            }

            Venta ventaGuardada = ventasService.guardar(venta);
            VentaGetDTO dto = VentaMapper.toDTO(ventaGuardada);
            return ResponseEntity.ok(new ApiResponse<>("Venta creada", dto, true));

        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}/anular")
    public ResponseEntity<?> anular(@PathVariable Integer id) {
        try {
            Venta venta = ventasService.obtener(id)
                    .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

            if (venta.getEstado() == EstadoVenta.ANULADA) {
                return new ResponseEntity<>(new ApiResponse<>("La venta ya está anulada", null, false), HttpStatus.BAD_REQUEST);
            }

            for (DetalleVenta d : venta.getDetalleventas()) {
                Medicamento m = d.getMedicamento();
                m.setStock(m.getStock() + d.getCantidad());
                medicamentoService.guardar(m);
            }

            venta.setEstado(EstadoVenta.ANULADA);
            venta.setActivo(false);
            Venta anulada = ventasService.guardar(venta);

            VentaGetDTO dto = VentaMapper.toDTO(anulada);
            return new ResponseEntity<>(new ApiResponse<>("Venta anulada", dto, true), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage(), null, false), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
