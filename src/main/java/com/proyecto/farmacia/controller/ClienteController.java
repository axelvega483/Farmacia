package com.proyecto.farmacia.controller;

import com.proyecto.farmacia.DTOs.Clientes.ClientePostDTO;
import com.proyecto.farmacia.DTOs.Clientes.ClienteUpdateDTO;
import com.proyecto.farmacia.DTOs.Clientes.ClientesGetDTO;
import com.proyecto.farmacia.interfaz.ClienteInterfaz;
import com.proyecto.farmacia.util.CustomApiResponse;
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

@CrossOrigin("*")
@RestController
@RequestMapping("cliente")
@Tag(name = "Clientes", description = "Controlador para operaciones de clientes")
public class ClienteController {

    @Autowired
    private ClienteInterfaz clienteService;

    @Operation(summary = "Listar todos los clientes",
            description = "Devuelve una lista con todos los clientes registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<ClientesGetDTO> dto = clienteService.findAll();
        return new ResponseEntity<>(new CustomApiResponse<>("Clientes obtenidos correctamente", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Obtener cliente por ID",
            description = "Devuelve un cliente específico basado en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("{id}")
    public ResponseEntity<?> findById(
            @Parameter(description = "ID del cliente a buscar", example = "1", required = true)
            @PathVariable Integer id) {
        ClientesGetDTO dto = clienteService.findById(id).orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        return new ResponseEntity<>(new CustomApiResponse<>("Cliente encontrado por id", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Crear nuevo cliente",
            description = "Crea un nuevo cliente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "409", description = "Cliente ya existente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> create(
            @Parameter(description = "Datos del cliente a crear", required = true)
            @Valid @RequestBody ClientePostDTO clienteDTO) {
        ClientesGetDTO dto = clienteService.create(clienteDTO);
        return new ResponseEntity<>(new CustomApiResponse<>("Cliente creado", dto, true), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar cliente existente",
            description = "Actualiza la información de un cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("{id}")
    public ResponseEntity<?> update(
            @Parameter(description = "ID del cliente a actualizar", example = "1", required = true)
            @PathVariable Integer id,

            @Parameter(description = "Datos actualizados del cliente", required = true)
            @Valid @RequestBody ClienteUpdateDTO clienteDTO) {

        ClientesGetDTO dto = clienteService.update(id, clienteDTO);
        return new ResponseEntity<>(new CustomApiResponse<>("Cliente actualizado", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar cliente",
            description = "Desactiva un cliente del sistema basado en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(
            @Parameter(description = "ID del cliente a eliminar", example = "1", required = true)
            @PathVariable Integer id) {
        clienteService.delete(id);
        return new ResponseEntity<>( new CustomApiResponse<>("Cliente eliminado correctamente", null, true), HttpStatus.OK);
    }
}