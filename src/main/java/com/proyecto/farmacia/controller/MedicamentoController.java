package com.proyecto.farmacia.controller;

import com.proyecto.farmacia.DTOs.Medicamentos.MedicamentoPostDTO;
import com.proyecto.farmacia.DTOs.Medicamentos.MedicamentoUpdateDTO;
import com.proyecto.farmacia.DTOs.Medicamentos.MedicamentosGetDTO;
import com.proyecto.farmacia.interfaz.MedicamentoInterfaz;
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

@CrossOrigin("*")
@RestController
@RequestMapping("medicamento")
public class MedicamentoController {

    @Autowired
    private MedicamentoInterfaz medicamentoService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<MedicamentosGetDTO> dto = medicamentoService.findAll();
            return new ResponseEntity<>(new ApiResponse<>("Medicamentos", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("nombre/{nombre}")
    public ResponseEntity<?> findByName(@PathVariable String nombre) {
        try {
            List<MedicamentosGetDTO> medicamentos = medicamentoService.findByName(nombre);
            if (medicamentos != null && !medicamentos.isEmpty()) {
                return new ResponseEntity<>(new ApiResponse<>("Medicamentos encontrados por nombre", medicamentos, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("No se encontraron coincidencia", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            MedicamentosGetDTO medicamento = medicamentoService.findById(id).orElse(null);
            if (medicamento != null) {
                return new ResponseEntity<>(new ApiResponse<>("Medicamento", medicamento, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("Medicamento inexistente", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody MedicamentoPostDTO medicamentoDTO) {
        try {
            MedicamentosGetDTO dto = medicamentoService.create(medicamentoDTO);
            return new ResponseEntity<>(new ApiResponse<>("Medicamento cargado", dto, true), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@Valid @RequestBody MedicamentoUpdateDTO medicamentoDTO, @PathVariable Integer id) {
        try {
            MedicamentosGetDTO dto = medicamentoService.update(id, medicamentoDTO);
            return new ResponseEntity<>(new ApiResponse<>("Medicamento actualizado", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            MedicamentosGetDTO medicamento = medicamentoService.findById(id).orElse(null);
            if (medicamento != null) {
                medicamentoService.delete(medicamento.getId());
                return new ResponseEntity<>(new ApiResponse<>("Medicamento eliminado", null, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("No se pudo eliminar", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
