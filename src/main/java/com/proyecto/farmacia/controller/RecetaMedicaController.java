package com.proyecto.farmacia.controller;

import com.proyecto.farmacia.DTOs.RecetaMedica.RecetaMedicaGetDTO;
import com.proyecto.farmacia.interfaz.RecetaMedicaInterfaz;
import com.proyecto.farmacia.util.CustomApiResponse;
import com.proyecto.farmacia.DTOs.RecetaMedica.RecetaMedicaPostDTO;
import com.proyecto.farmacia.DTOs.RecetaMedica.RecetaMedicaUptadeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("receta")
@Tag(name = "Recetas Médicas", description = "Controlador para operaciones de recetas médicas")
public class RecetaMedicaController {

    @Autowired
    private RecetaMedicaInterfaz recetaService;

    @Operation(summary = "Listar todas las recetas médicas", description = "Devuelve una lista con todas las recetas médicas registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recetas médicas listadas correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<RecetaMedicaGetDTO> dto = recetaService.findAll();
        return new ResponseEntity<>(new CustomApiResponse<>("Receta Medica", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Obtener receta médica por ID", description = "Devuelve una receta médica específica basada en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receta médica encontrada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Receta médica no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("{id}")
    public ResponseEntity<?> findById(
            @Parameter(description = "ID de la receta médica a buscar", example = "1", required = true)
            @PathVariable Integer id) {
        RecetaMedicaGetDTO receta = recetaService.findById(id).orElse(null);
        if (receta != null) {
            return new ResponseEntity<>(new CustomApiResponse<>("Receta Medica", receta, true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomApiResponse<>("Receta Medica no encontrada", null, false), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Crear nueva receta médica", description = "Registra una nueva receta médica en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Receta médica creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping()
    public ResponseEntity<?> create(
            @Parameter(description = "Datos de la receta médica a crear", required = true)
            @Valid @RequestBody RecetaMedicaPostDTO recetaDto) {
        RecetaMedicaGetDTO dto = recetaService.create(recetaDto);
        return new ResponseEntity<>(new CustomApiResponse<>("Receta medica cargada", dto, true), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar receta médica existente", description = "Actualiza la información de una receta médica existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receta médica actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Receta médica no encontrada"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("{id}")
    public ResponseEntity<?> update(
            @Parameter(description = "Datos actualizados de la receta médica", required = true)
            @Valid @RequestBody RecetaMedicaUptadeDTO recetaMedicaDto,
            @Parameter(description = "ID de la receta médica a actualizar", example = "1", required = true)
            @PathVariable Integer id) {
        RecetaMedicaGetDTO dto = recetaService.update(id, recetaMedicaDto);
        return new ResponseEntity<>(new CustomApiResponse<>("Actualizada", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar receta médica", description = "Elimina una receta médica del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receta médica eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Receta médica no encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflicto al eliminar la receta médica"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(
            @Parameter(description = "ID de la receta médica a eliminar", example = "1", required = true)
            @PathVariable Integer id) {
        RecetaMedicaGetDTO recetaMedica = recetaService.findById(id).orElse(null);
        if (recetaMedica != null) {
            recetaService.delete(recetaMedica.getId());
            return new ResponseEntity<>(new CustomApiResponse<>("receta medica eliminada", null, true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomApiResponse<>("no se pudo dar de baja", null, false), HttpStatus.NOT_FOUND);
        }
    }
}