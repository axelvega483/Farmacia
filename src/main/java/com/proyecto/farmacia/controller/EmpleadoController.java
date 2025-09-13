package com.proyecto.farmacia.controller;

import com.proyecto.farmacia.DTOs.Empleados.EmpleadoGetDTO;
import com.proyecto.farmacia.DTOs.Empleados.EmpleadoPostDTO;
import com.proyecto.farmacia.DTOs.Empleados.EmpleadoUpdateDTO;
import com.proyecto.farmacia.entity.Empleado;
import com.proyecto.farmacia.interfaz.EmpleadoInterfaz;
import com.proyecto.farmacia.util.ApiResponse;
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
public class EmpleadoController {

    @Autowired
    private EmpleadoInterfaz empleadoService;


    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<EmpleadoGetDTO> dto = empleadoService.findAll();
            return new ResponseEntity<>(new ApiResponse<>("Empleados", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            EmpleadoGetDTO empleado = empleadoService.findById(id).orElse(null);
            if (empleado != null) {
                return new ResponseEntity<>(new ApiResponse<>("Empleado", empleado, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("Empleado no encontrado", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody EmpleadoPostDTO postDTO) {
        try {
            Optional<Empleado> optionalEmpleado = empleadoService.findByCorreoAndPassword(postDTO.getEmail(), postDTO.getPassword());
            if (optionalEmpleado.isPresent()) {
                Empleado empleado = optionalEmpleado.get();
                return new ResponseEntity<>(new ApiResponse<>("Login correcto", empleado, true), HttpStatus.OK);

            }
            return new ResponseEntity<>(new ApiResponse<>("Credenciales no encontradas", null, false), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody EmpleadoPostDTO empleadoDTO) {
        try {
            empleadoService.create(empleadoDTO);
            return new ResponseEntity<>(new ApiResponse<>("Empleado creado", empleadoDTO, true), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@Valid @RequestBody EmpleadoUpdateDTO empleadoDTO, @PathVariable Integer id) {
        try {
            EmpleadoGetDTO dto = empleadoService.update(id, empleadoDTO);
            return new ResponseEntity<>(new ApiResponse<>("Empleado actualizado", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            EmpleadoGetDTO empleado = empleadoService.findById(id).orElse(null);
            if (empleado != null) {
                EmpleadoGetDTO dto =   empleadoService.delete(id);
                return new ResponseEntity<>(new ApiResponse<>("Empleado inactivo", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("No se encontro empleado", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
