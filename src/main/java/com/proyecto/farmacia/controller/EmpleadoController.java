package com.proyecto.farmacia.controller;

import com.proyecto.farmacia.DTOs.Empleados.EmpleadoGetDTO;
import com.proyecto.farmacia.DTOs.Empleados.EmpleadoMapper;
import com.proyecto.farmacia.DTOs.Empleados.EmpleadoPostDTO;
import com.proyecto.farmacia.DTOs.Empleados.EmpleadoUpdateDTO;
import com.proyecto.farmacia.entity.Empleado;
import com.proyecto.farmacia.entity.Venta;
import com.proyecto.farmacia.service.EmpleadoService;
import com.proyecto.farmacia.service.VentasService;
import com.proyecto.farmacia.util.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private EmpleadoService empleadoService;

    @Autowired
    private VentasService ventaService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            List<EmpleadoGetDTO> dto = empleadoService.listar().stream()
                    .map(EmpleadoMapper::toDTO)
                    .toList();
            return new ResponseEntity<>(new ApiResponse<>("Empleados", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{id}")
    public ResponseEntity<?> obtner(@PathVariable Integer id) {
        try {
            Empleado empleado = empleadoService.obtener(id).orElse(null);
            if (empleado != null) {
                EmpleadoGetDTO dto = EmpleadoMapper.toDTO(empleado);
                return new ResponseEntity<>(new ApiResponse<>("Empleado", dto, true), HttpStatus.OK);
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
            Optional<Empleado> optionalEmpleado = empleadoService.findByCorreo(postDTO.getEmail());
            if (optionalEmpleado.isPresent()) {
                Empleado empleado = optionalEmpleado.get();
                if (empleado.getActivo() && passwordEncoder.matches(postDTO.getPassword(), empleado.getPassword())) {
                    return new ResponseEntity<>(new ApiResponse<>("Login correcto", null, true), HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(new ApiResponse<>("Credenciales no encontradas", null, false), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<?> crear(@Valid @RequestBody EmpleadoPostDTO empleadoDTO) {
        try {
            if (empleadoService.existe(empleadoDTO.getDni())) {
                return new ResponseEntity<>(new ApiResponse<>("Empleado ya existente", empleadoDTO, false), HttpStatus.CONFLICT);
            } else {

                List<Venta> ventas = ventaService.obtenerById(empleadoDTO.getVentasID());
                if (ventas.size() != empleadoDTO.getVentasID().size()) {
                    return new ResponseEntity<>(new ApiResponse<>("Uno o más ventas no existen", null, false), HttpStatus.BAD_REQUEST);
                }
                Empleado empleado = new Empleado();
                empleado.setActivo(Boolean.TRUE);
                empleado.setDni(empleadoDTO.getDni());
                empleado.setEmail(empleadoDTO.getEmail());
                empleado.setNombre(empleadoDTO.getNombre());
                empleado.setPassword(empleadoDTO.getPassword());
                empleado.setRol(empleadoDTO.getRol());
                empleado.setVentas(ventas);
                EmpleadoGetDTO dto = EmpleadoMapper.toDTO(empleadoService.guardar(empleado));
                return new ResponseEntity<>(new ApiResponse<>("Empleado cargado", dto, true), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody EmpleadoUpdateDTO empleadoDTO, @PathVariable Integer id) {
        try {
            Empleado empleado = empleadoService.obtener(id).orElse(null);
            if (empleado != null) {
                List<Venta> ventas = ventaService.obtenerById(empleadoDTO.getVentasID());
                if (ventas.size() != empleadoDTO.getVentasID().size()) {
                    return new ResponseEntity<>(new ApiResponse<>("Uno o más ventas no existen", null, false), HttpStatus.BAD_REQUEST);
                }
                empleado.setActivo(Boolean.TRUE);
                empleado.setDni(empleadoDTO.getDni());
                empleado.setEmail(empleadoDTO.getEmail());
                empleado.setNombre(empleadoDTO.getNombre());
                empleado.setPassword(empleadoDTO.getPassword());
                empleado.setRol(empleadoDTO.getRol());
                empleado.setVentas(ventas);
                EmpleadoGetDTO dto = EmpleadoMapper.toDTO(empleadoService.guardar(empleado));

                return new ResponseEntity<>(new ApiResponse<>("Empleado actualizado", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("No se encontro empleado", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            Empleado empleado = empleadoService.obtener(id).orElse(null);
            if (empleado != null) {
                empleadoService.eliminar(id);
                EmpleadoGetDTO dto = EmpleadoMapper.toDTO(empleadoService.guardar(empleado));
                return new ResponseEntity<>(new ApiResponse<>("Empleado inactivo", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("No se encontro empleado", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
