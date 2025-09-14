package com.proyecto.farmacia.controller;

import com.proyecto.farmacia.interfaz.VentaInterfaz;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.FileInputStream;

import com.proyecto.farmacia.DTOs.Ventas.VentaGetDTO;
import com.proyecto.farmacia.DTOs.Ventas.VentaPostDTO;
import com.proyecto.farmacia.service.PdfGeneratorService;
import com.proyecto.farmacia.util.ApiResponse;
import jakarta.validation.Valid;

import java.io.File;
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
    private VentaInterfaz ventasService;

    @Autowired
    private PdfGeneratorService pdfGenerator;


    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<VentaGetDTO> dto = ventasService.findAll();
            return new ResponseEntity<>(new ApiResponse<>("Ventas", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            VentaGetDTO venta = ventasService.findById(id).orElse(null);
            if (venta != null) {
                return new ResponseEntity<>(new ApiResponse<>("Venta", venta, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("No se encontro venta", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody VentaPostDTO ventaDTO) {
        try {
            VentaGetDTO dto = ventasService.create(ventaDTO);
            return ResponseEntity.ok(new ApiResponse<>("Venta creada", dto, true));
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/anular/{id}")
    public ResponseEntity<?> cancel(@PathVariable Integer id) {
        try {
            VentaGetDTO venta = ventasService.cancel(id);
            return new ResponseEntity<>(new ApiResponse<>("Venta anulada", venta, true), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage(), null, false), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/factura/{id}")
    public ResponseEntity<?> descargarFactura(@PathVariable Integer id) {
        try {
            VentaGetDTO venta = ventasService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

            String rutaPDF = pdfGenerator.generarFacturaPDF(venta);

            File pdfFile = new File(rutaPDF);
            if (!pdfFile.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>("Factura no generada correctamente", null, false));
            }

            InputStreamResource resource = new InputStreamResource(new FileInputStream(pdfFile));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=factura-" + venta.getId() + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(pdfFile.length())
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("Error al generar factura: " + e.getMessage(), null, false));
        }
    }
}
