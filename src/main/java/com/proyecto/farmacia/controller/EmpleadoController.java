package com.proyecto.farmacia.controller;

import com.proyecto.farmacia.DTOs.Empleados.EmpleadoGetDTO;
import com.proyecto.farmacia.DTOs.Empleados.EmpleadoPostDTO;
import com.proyecto.farmacia.DTOs.Empleados.EmpleadoUpdateDTO;
import com.proyecto.farmacia.entity.Empleado;
import com.proyecto.farmacia.interfaz.EmpleadoInterfaz;
import com.proyecto.farmacia.util.CustomApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

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
@RequestMapping("empleado")
@Tag(name = "Empleados", description = "Controlador para operaciones de empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoInterfaz empleadoService;

    @Operation(summary = "Listar todos los empleados", description = "Devuelve una lista con todos los empleados registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleados listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<EmpleadoGetDTO> dto = empleadoService.findAll();
        return new ResponseEntity<>(new CustomApiResponse<>("Empleados", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Obtener empleado por ID", description = "Devuelve un empleado específico basado en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("{id}")
    public ResponseEntity<?> findById(
            @Parameter(description = "ID del empleado a buscar", example = "1", required = true)
            @PathVariable Integer id) {
        EmpleadoGetDTO empleado = empleadoService.findById(id).orElse(null);
        if (empleado != null) {
            return new ResponseEntity<>(new CustomApiResponse<>("Empleado", empleado, true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomApiResponse<>("Empleado no encontrado", null, false), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Login de empleado", description = "Autentica un empleado con correo y contraseña")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Parameter(description = "Credenciales de acceso", required = true)
            @RequestBody EmpleadoPostDTO postDTO) {
        Optional<Empleado> optionalEmpleado = empleadoService.findByCorreoAndPassword(postDTO.getEmail(), postDTO.getPassword());
        if (optionalEmpleado.isPresent()) {
            Empleado empleado = optionalEmpleado.get();
            return new ResponseEntity<>(new CustomApiResponse<>("Login correcto", empleado, true), HttpStatus.OK);
        }
        return new ResponseEntity<>(new CustomApiResponse<>("Credenciales no encontradas", null, false), HttpStatus.UNAUTHORIZED);
    }

    @Operation(summary = "Crear nuevo empleado", description = "Registra un nuevo empleado en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empleado creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping()
    public ResponseEntity<?> create(
            @Parameter(description = "Datos del empleado a crear", required = true)
            @Valid @RequestBody EmpleadoPostDTO empleadoDTO) {
        empleadoService.create(empleadoDTO);
        return new ResponseEntity<>(new CustomApiResponse<>("Empleado creado", empleadoDTO, true), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar empleado existente", description = "Actualiza la información de un empleado existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("{id}")
    public ResponseEntity<?> update(
            @Parameter(description = "Datos actualizados del empleado", required = true)
            @Valid @RequestBody EmpleadoUpdateDTO empleadoDTO,
            @Parameter(description = "ID del empleado a actualizar", example = "1", required = true)
            @PathVariable Integer id) {
        EmpleadoGetDTO dto = empleadoService.update(id, empleadoDTO);
        return new ResponseEntity<>(new CustomApiResponse<>("Empleado actualizado", dto, true), HttpStatus.OK);
    }

    @Operation(summary = "Desactivar empleado", description = "Marca un empleado como inactivo en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empleado desactivado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Empleado no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(
            @Parameter(description = "ID del empleado a desactivar", example = "1", required = true)
            @PathVariable Integer id) {
        EmpleadoGetDTO empleado = empleadoService.findById(id).orElse(null);
        if (empleado != null) {
            EmpleadoGetDTO dto = empleadoService.delete(id);
            return new ResponseEntity<>(new CustomApiResponse<>("Empleado inactivo", dto, true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new CustomApiResponse<>("No se encontro empleado", null, false), HttpStatus.NOT_FOUND);
        }
    }
}
