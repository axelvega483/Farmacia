package com.proyecto.farmacia.controller;

import com.proyecto.farmacia.DTOs.Usuarios.UsuarioGetDTO;
import com.proyecto.farmacia.DTOs.Usuarios.UsuarioPostDTO;
import com.proyecto.farmacia.DTOs.Usuarios.UsuarioRolDTO;
import com.proyecto.farmacia.DTOs.Usuarios.UsuarioUpdateDTO;
import com.proyecto.farmacia.interfaz.UsuarioInterfaz;
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
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("usuario")
@Tag(name = "Usuarios", description = "Controlador para operaciones de usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioInterfaz usuarioService;

    @Operation(summary = "Listar todos los usuarios", description = "Devuelve una lista con todos los usuarios registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuarios listados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<UsuarioGetDTO> dto = usuarioService.findAll();
        return new ResponseEntity<>(ApiRespons.ok("Usuarios obtenidos correctamente", dto), HttpStatus.OK);
    }

    @Operation(summary = "Obtener usuario por ID", description = "Devuelve un usuario específico basado en su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @PreAuthorize("hasRole('ADMIN') or @usuarioSecurity.isCurrentUser(#id)")
    @GetMapping("{id}")
    public ResponseEntity<?> findById(
            @Parameter(description = "ID del usuario a buscar", example = "1", required = true) @PathVariable Integer id) {
        UsuarioGetDTO dto = usuarioService.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        return new ResponseEntity<>(ApiRespons.ok("Usuario encontrado con éxito", dto), HttpStatus.OK);
    }

    @Operation(summary = "Crear nuevo usuario", description = "Registra un nuevo usuario en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "409", description = "El usuario ya existe"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> create(@Parameter(description = "Datos del usuario a crear", required = true) @Valid @RequestBody UsuarioPostDTO usuarioDTO) {
        UsuarioGetDTO dto = usuarioService.create(usuarioDTO);
        return new ResponseEntity<>(ApiRespons.ok("Usuario creado", dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar empleado existente", description = "Actualiza la información de un usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @PreAuthorize("hasRole('ADMIN') or @usuarioSecurity.isCurrentUser(#id)")
    @PutMapping("{id}")
    public ResponseEntity<?> update(@Parameter(description = "ID del usuario a actualizar", example = "1", required = true) @PathVariable Integer id, @Parameter(description = "Datos actualizados del usuario", required = true) @Valid @RequestBody UsuarioUpdateDTO usuarioDTO) {
        UsuarioGetDTO dto = usuarioService.update(id, usuarioDTO);
        return new ResponseEntity<>(ApiRespons.ok("Usuario actualizado", dto), HttpStatus.OK);
    }

    @Operation(summary = "Actualizar rol", description = "Cambia el rol de un usuario existente")
    @ApiResponses(value = {@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuario no encontrado"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"), @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}/rol")
    public ResponseEntity<?> actualizarRolUsuario(@Parameter(description = "ID del usuario a actualizar", example = "1", required = true) @PathVariable Integer id, @Parameter(description = "Datos actualizados del usuario", required = true) @RequestBody UsuarioRolDTO usuarioDTO) {
        UsuarioGetDTO dto = usuarioService.actualizarRol(id, usuarioDTO);
        return new ResponseEntity<>(ApiRespons.ok("Rol actualizado", dto), HttpStatus.OK);
    }

    @Operation(summary = "Desactivar usuario", description = "Marca un usuario como inactivo en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario desactivado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")})
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@Parameter(description = "ID del usuario a desactivar", example = "1", required = true) @PathVariable Integer id) {
        UsuarioGetDTO dto = usuarioService.delete(id);
        return new ResponseEntity<>(ApiRespons.ok("Usuario inactivo", dto), HttpStatus.OK);
    }
}