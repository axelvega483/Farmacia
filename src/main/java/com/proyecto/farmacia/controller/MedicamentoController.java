package com.proyecto.farmacia.controller;

import com.proyecto.farmacia.DTOs.Medicamentos.MedicamentoPostDTO;
import com.proyecto.farmacia.DTOs.Medicamentos.MedicamentoUpdateDTO;
import com.proyecto.farmacia.DTOs.Medicamentos.MedicamentosGetDTO;
import com.proyecto.farmacia.interfaz.MedicamentoInterfaz;
import com.proyecto.farmacia.util.CustomApiResponse;
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

@CrossOrigin("*")
@RestController
@RequestMapping("medicamento")
@Tag(name = "Medicamentos", description = "Controlador para operaciones de medicamentos")
public class MedicamentoController {

    @Autowired
    private MedicamentoInterfaz medicamentoService;

    @Operation(summary = "Listar todos los medicamentos", description = "Devuelve una lista con todos los medicamentos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicamentos listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<MedicamentosGetDTO> dto = medicamentoService.findAll();
        return new ResponseEntity<>(new CustomApiResponse<>("Medicamentos", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Buscar medicamentos por nombre", description = "Devuelve medicamentos que coincidan con el nombre proporcionado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicamentos encontrados exitosamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron coincidencias"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("nombre/{nombre}")
    public ResponseEntity<?> findByName(
            @Parameter(description = "Nombre del medicamento a buscar", example = "paracetamol", required = true)
            @PathVariable String nombre) {
        List<MedicamentosGetDTO> medicamentos = medicamentoService.findByName(nombre);
        if (medicamentos != null && !medicamentos.isEmpty()) {
            return new ResponseEntity<>(new CustomApiResponse<>("Medicamentos encontrados por nombre", medicamentos, true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomApiResponse<>("No se encontraron coincidencias", null, false), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Obtener medicamento por ID", description = "Devuelve un medicamento específico basado en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicamento encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Medicamento no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("{id}")
    public ResponseEntity<?> findById(
            @Parameter(description = "ID del medicamento a buscar", example = "1", required = true)
            @PathVariable Integer id) {
        MedicamentosGetDTO medicamento = medicamentoService.findById(id).orElse(null);
        if (medicamento != null) {
            return new ResponseEntity<>(new CustomApiResponse<>("Medicamento", medicamento, true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomApiResponse<>("Medicamento inexistente", null, false), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Crear nuevo medicamento", description = "Registra un nuevo medicamento en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Medicamento creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> create(
            @Parameter(description = "Datos del medicamento a crear", required = true)
            @Valid @RequestBody MedicamentoPostDTO medicamentoDTO) {
        MedicamentosGetDTO dto = medicamentoService.create(medicamentoDTO);
        return new ResponseEntity<>(new CustomApiResponse<>("Medicamento cargado", dto, true), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar medicamento existente", description = "Actualiza la información de un medicamento existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicamento actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Medicamento no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("{id}")
    public ResponseEntity<?> update(
            @Parameter(description = "Datos actualizados del medicamento", required = true)
            @Valid @RequestBody MedicamentoUpdateDTO medicamentoDTO,
            @Parameter(description = "ID del medicamento a actualizar", example = "1", required = true)
            @PathVariable Integer id) {
        MedicamentosGetDTO dto = medicamentoService.update(id, medicamentoDTO);
        return new ResponseEntity<>(new CustomApiResponse<>("Medicamento actualizado", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar medicamento", description = "Elimina un medicamento del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medicamento eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Medicamento no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(
            @Parameter(description = "ID del medicamento a eliminar", example = "1", required = true)
            @PathVariable Integer id) {
        MedicamentosGetDTO medicamento = medicamentoService.findById(id).orElse(null);
        if (medicamento != null) {
            medicamentoService.delete(medicamento.getId());
            return new ResponseEntity<>(new CustomApiResponse<>("Medicamento eliminado", null, true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomApiResponse<>("No se pudo eliminar", null, false), HttpStatus.NOT_FOUND);
        }
    }
}