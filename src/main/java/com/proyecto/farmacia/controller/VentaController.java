package com.proyecto.farmacia.controller;

import com.proyecto.farmacia.interfaz.VentaInterfaz;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.FileInputStream;

import com.proyecto.farmacia.DTOs.Ventas.VentaGetDTO;
import com.proyecto.farmacia.DTOs.Ventas.VentaPostDTO;
import com.proyecto.farmacia.service.PdfGeneratorService;
import com.proyecto.farmacia.util.CustomApiResponse;
import jakarta.validation.Valid;

import java.io.File;
import java.io.FileNotFoundException;
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
@Tag(name = "Ventas", description = "Controlador para operaciones de ventas")
public class VentaController {

    @Autowired
    private VentaInterfaz ventasService;

    @Autowired
    private PdfGeneratorService pdfGenerator;

    @Operation(summary = "Listar todas las ventas", description = "Devuelve una lista con todas las ventas registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ventas listadas correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<VentaGetDTO> dto = ventasService.findAll();
        return new ResponseEntity<>(new CustomApiResponse<>("Ventas", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Obtener venta por ID", description = "Devuelve una venta específica basada en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta encontrada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("{id}")
    public ResponseEntity<?> findById(
            @Parameter(description = "ID de la venta a buscar", example = "1", required = true)
            @PathVariable Integer id) {
        VentaGetDTO venta = ventasService.findById(id).orElse(null);
        if (venta != null) {
            return new ResponseEntity<>(new CustomApiResponse<>("Venta", venta, true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomApiResponse<>("No se encontro venta", null, false), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Crear nueva venta", description = "Registra una nueva venta en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> create(
            @Parameter(description = "Datos de la venta a crear", required = true)
            @Valid @RequestBody VentaPostDTO ventaDTO) {
        VentaGetDTO dto = ventasService.create(ventaDTO);
        return ResponseEntity.ok(new CustomApiResponse<>("Venta creada", dto, true));
    }

    @Operation(summary = "Anular venta", description = "Anula una venta existente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta anulada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/anular/{id}")
    public ResponseEntity<?> cancel(
            @Parameter(description = "ID de la venta a anular", example = "1", required = true)
            @PathVariable Integer id) {
        VentaGetDTO venta = ventasService.cancel(id);
        return new ResponseEntity<>(new CustomApiResponse<>("Venta anulada", venta, true), HttpStatus.OK);
    }

    @Operation(summary = "Descargar factura PDF", description = "Genera y descarga la factura en formato PDF de una venta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Factura generada y descargada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada o factura no generada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor al generar la factura")
    })
    @GetMapping("/factura/{id}")
    public ResponseEntity<?> descargarFactura(
            @Parameter(description = "ID de la venta para generar factura", example = "1", required = true)
            @PathVariable Integer id) {
        VentaGetDTO venta = ventasService.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        String rutaPDF = pdfGenerator.generarFacturaPDF(venta);

        File pdfFile = new File(rutaPDF);
        if (!pdfFile.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CustomApiResponse<>("Factura no generada correctamente", null, false));
        }

        InputStreamResource resource;
        try {
            resource = new InputStreamResource(new FileInputStream(pdfFile));
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomApiResponse<>("Error al acceder al archivo PDF", null, false));
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=factura-" + venta.getId() + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdfFile.length())
                .body(resource);
    }
}