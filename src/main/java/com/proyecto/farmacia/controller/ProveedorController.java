package com.proyecto.farmacia.controller;

import com.proyecto.farmacia.DTOs.Proveedor.ProveedorGetDTO;
import com.proyecto.farmacia.DTOs.Proveedor.ProveedorPostDTO;
import com.proyecto.farmacia.DTOs.Proveedor.ProveedorUpdateDTO;
import com.proyecto.farmacia.interfaz.ProveedorInterfaz;
import com.proyecto.farmacia.util.ApiRespons;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
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
@RequestMapping("proveedor")
@Tag(name = "Proveedores", description = "Controlador para operaciones de proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorInterfaz proveedorService;

    @Operation(summary = "Listar todos los proveedores",
            description = "Devuelve una lista con todos los proveedores registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedores listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<ProveedorGetDTO> proveedores = proveedorService.findAll();
        return new ResponseEntity<>(ApiRespons.ok("Proveedores obtenidos correctamente", proveedores), HttpStatus.OK);
    }

    @Operation(summary = "Obtener proveedor por ID",
            description = "Devuelve un proveedor específico basado en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("{id}")
    public ResponseEntity<?> findById(
            @Parameter(description = "ID del proveedor a buscar", example = "1", required = true)
            @PathVariable Integer id) {
        ProveedorGetDTO proveedor = proveedorService.findById(id).orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));
        return new ResponseEntity<>(ApiRespons.ok("Proveedor encontrado", proveedor), HttpStatus.OK);
    }

    @Operation(summary = "Crear nuevo proveedor",
            description = "Registra un nuevo proveedor en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Proveedor creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "409", description = "Proveedor ya existente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> create(
            @Parameter(description = "Datos del proveedor a crear", required = true)
            @Valid @RequestBody ProveedorPostDTO proveedorDTO) {
        ProveedorGetDTO dto = proveedorService.create(proveedorDTO);
        return new ResponseEntity<>(ApiRespons.ok("Proveedor creado correctamente", dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar proveedor existente",
            description = "Actualiza la información de un proveedor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("{id}")
    public ResponseEntity<?> update(
            @Parameter(description = "Datos actualizados del proveedor", required = true)
            @Valid @RequestBody ProveedorUpdateDTO proveedorDTO,

            @Parameter(description = "ID del proveedor a actualizar", example = "1", required = true)
            @PathVariable Integer id) {
        ProveedorGetDTO dto = proveedorService.update(id, proveedorDTO);
        return new ResponseEntity<>(ApiRespons.ok("Proveedor actualizado correctamente", dto), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar proveedor",
            description = "Desactiva un proveedor del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(
            @Parameter(description = "ID del proveedor a eliminar", example = "1", required = true)
            @PathVariable Integer id) {
        ProveedorGetDTO dto = proveedorService.delete(id);
        return new ResponseEntity<>(ApiRespons.ok("Proveedor eliminado correctamente", dto), HttpStatus.OK);
    }
}