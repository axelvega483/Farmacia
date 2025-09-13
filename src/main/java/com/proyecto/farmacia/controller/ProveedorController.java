package com.proyecto.farmacia.controller;

import com.proyecto.farmacia.DTOs.Proveedor.ProveedorGetDTO;
import com.proyecto.farmacia.DTOs.Proveedor.ProveedorPostDTO;
import com.proyecto.farmacia.DTOs.Proveedor.ProveedorUpdateDTO;
import com.proyecto.farmacia.interfaz.ProveedorInterfaz;
import com.proyecto.farmacia.util.ApiResponse;
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
public class ProveedorController {

    @Autowired
    private ProveedorInterfaz proveedorService;


    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<ProveedorGetDTO> dto = proveedorService.findAll();
            return new ResponseEntity<>(new ApiResponse<>("Proveedor", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            ProveedorGetDTO proveedor = proveedorService.findById(id).orElse(null);
            if (proveedor != null) {
                return new ResponseEntity<>(new ApiResponse<>("Proveedor", proveedor, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("No existe proveedor", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ProveedorPostDTO proveedorDTO) {
        try {
            ProveedorGetDTO dto = proveedorService.create(proveedorDTO);
            return new ResponseEntity<>(new ApiResponse<>("Proveedor cargado", dto, true), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@Valid @RequestBody ProveedorUpdateDTO proveedorDTO, @PathVariable Integer id) {
        try {
            ProveedorGetDTO dto = proveedorService.update(id, proveedorDTO);
            return new ResponseEntity<>(new ApiResponse<>("Proveedor actualizado", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            ProveedorGetDTO proveedor = proveedorService.findById(id).orElse(null);
            if (proveedor != null) {
                proveedorService.delete(proveedor.getId());
                return new ResponseEntity<>(new ApiResponse<>("Proveedor eliminado", null, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("No se pudo eliminar", proveedor, false), HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
