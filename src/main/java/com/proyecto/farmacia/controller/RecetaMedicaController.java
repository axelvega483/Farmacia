package com.proyecto.farmacia.controller;

import com.proyecto.farmacia.DTOs.RecetaMedica.RecetaMedicaGetDTO;
import com.proyecto.farmacia.interfaz.RecetaMedicaInterfaz;
import com.proyecto.farmacia.util.ApiResponse;
import com.proyecto.farmacia.DTOs.RecetaMedica.RecetaMedicaPostDTO;
import com.proyecto.farmacia.DTOs.RecetaMedica.RecetaMedicaUptadeDTO;
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
public class RecetaMedicaController {

    @Autowired
    private RecetaMedicaInterfaz recetaService;


    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<RecetaMedicaGetDTO> dto = recetaService.findAll();
            return new ResponseEntity<>(new ApiResponse<>("Receta Medica", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            RecetaMedicaGetDTO receta = recetaService.findById(id).orElse(null);
            if (receta != null) {
                return new ResponseEntity<>(new ApiResponse<>("Receta Medica", receta, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("Receta Medica no encontrada", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody RecetaMedicaPostDTO recetaDto) {
        try {
            RecetaMedicaGetDTO dto = recetaService.create(recetaDto);
            return new ResponseEntity<>(new ApiResponse<>("Receta medica cargada", dto, true), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage(), null, false), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@Valid @RequestBody RecetaMedicaUptadeDTO recetaMedicaDto, @PathVariable Integer id) {
        try {
            RecetaMedicaGetDTO dto = recetaService.update(id,recetaMedicaDto);
            return new ResponseEntity<>(new ApiResponse<>("Actualizada", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id
    ) {
        try {
            RecetaMedicaGetDTO recetaMedica = recetaService.findById(id).orElse(null);
            if (recetaMedica != null) {
                recetaService.delete(recetaMedica.getId());
                return new ResponseEntity<>(new ApiResponse<>("receta medica eliminada", null, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("no se pudo dar de baja", recetaMedica, false), HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
