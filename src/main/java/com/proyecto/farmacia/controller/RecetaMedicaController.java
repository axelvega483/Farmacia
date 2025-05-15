package com.proyecto.farmacia.controller;

import com.proyecto.farmacia.DTOs.RecetaMedica.RecetaMedicaGetDTO;
import com.proyecto.farmacia.DTOs.RecetaMedica.RecetaMedicaMapper;
import com.proyecto.farmacia.entity.Cliente;
import com.proyecto.farmacia.entity.Medicamento;
import com.proyecto.farmacia.entity.RecetaMedica;
import com.proyecto.farmacia.service.ClienteService;
import com.proyecto.farmacia.service.MedicamentoService;
import com.proyecto.farmacia.service.RecetaMedicaService;
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
    private RecetaMedicaService recetaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private MedicamentoService medicamentoService;

    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            List<RecetaMedicaGetDTO> dto = recetaService.listar()
                    .stream()
                    .map(RecetaMedicaMapper::toDTO)
                    .toList();
            return new ResponseEntity<>(new ApiResponse<>("Receta Medica", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> obtener(@PathVariable Integer id) {
        try {
            RecetaMedica receta = recetaService.obtener(id).orElse(null);
            if (receta != null) {
                RecetaMedicaGetDTO dto = RecetaMedicaMapper.toDTO(receta);
                return new ResponseEntity<>(new ApiResponse<>("Receta Medica", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("Receta Medica no encontrada", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<?> cargar(@Valid @RequestBody RecetaMedicaPostDTO recetaDto) {
        try {
            Cliente cliente = clienteService.obtener(recetaDto.getClienteId()).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

            List<Medicamento> medicamentos = medicamentoService.obtenerById(recetaDto.getMedicamentoIds());
            if (medicamentos.size() != recetaDto.getMedicamentoIds().size()) {
                return new ResponseEntity<>(new ApiResponse<>("Uno o más medicamentos no existen", null, false), HttpStatus.BAD_REQUEST);
            }
            if (recetaDto.getVigenteHasta().isBefore(recetaDto.getFecha())) {
                return new ResponseEntity<>(new ApiResponse<>("La fecha de vigencia no puede ser anterior a la fecha de emisión", null, false), HttpStatus.BAD_REQUEST);
            }
            RecetaMedica receta = new RecetaMedica();
            receta.setCliente(cliente);
            receta.setFecha(recetaDto.getFecha());
            receta.setMedicamentos(medicamentos);
            receta.setMedico(recetaDto.getMedico());
            receta.setVigenteHasta(recetaDto.getVigenteHasta());
            receta.setActivo(Boolean.TRUE);

            RecetaMedicaGetDTO dto = RecetaMedicaMapper.toDTO(recetaService.guardar(receta));

            return new ResponseEntity<>(new ApiResponse<>("Receta medica cargada", dto, true), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ApiResponse<>(e.getMessage(), null, false), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody RecetaMedicaUptadeDTO recetaMedicaDto, @PathVariable Integer id) {
        try {
            RecetaMedica recetaDB = recetaService.obtener(id).orElse(null);
            if (recetaDB == null) {
                return new ResponseEntity<>(new ApiResponse<>("Receta medica no encontrada", null, false), HttpStatus.NOT_FOUND);
            }
            Cliente cliente = clienteService.obtener(recetaMedicaDto.getClienteId()).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
            List<Medicamento> medicamentos = medicamentoService.obtenerById(recetaMedicaDto.getMedicamentoIds());
            if (medicamentos.size() != recetaMedicaDto.getMedicamentoIds().size()) {
                return new ResponseEntity<>(new ApiResponse<>("Uno o más medicamentos no existen", null, false), HttpStatus.BAD_REQUEST);
            }
            if (recetaMedicaDto.getVigenteHasta().isBefore(recetaMedicaDto.getFecha())) {
                return new ResponseEntity<>(new ApiResponse<>("La fecha de vigencia no puede ser anterior a la fecha de emisión", null, false), HttpStatus.BAD_REQUEST);
            }

            recetaDB.setCliente(cliente);
            recetaDB.setFecha(recetaMedicaDto.getFecha());
            recetaDB.setMedico(recetaMedicaDto.getMedico());
            recetaDB.setVigenteHasta(recetaMedicaDto.getVigenteHasta());
            recetaDB.setMedicamentos(medicamentos);

            RecetaMedicaGetDTO dto = RecetaMedicaMapper.toDTO(recetaService.guardar(recetaDB));

            return new ResponseEntity<>(new ApiResponse<>("Actualizada", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id
    ) {
        try {
            RecetaMedica recetaMedica = recetaService.obtener(id).orElse(null);
            if (recetaMedica != null) {
                recetaService.eliminar(recetaMedica.getId());
                RecetaMedicaGetDTO dto = RecetaMedicaMapper.toDTO(recetaMedica);
                return new ResponseEntity<>(new ApiResponse<>("receta medica eliminada", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("no se pudo dar de baja", recetaMedica, false), HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
