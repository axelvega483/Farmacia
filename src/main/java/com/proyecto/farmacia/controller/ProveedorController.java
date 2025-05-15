package com.proyecto.farmacia.controller;

import com.proyecto.farmacia.DTOs.Proveedor.ProveedorGetDTO;
import com.proyecto.farmacia.DTOs.Proveedor.ProveedorMapper;
import com.proyecto.farmacia.DTOs.Proveedor.ProveedorPostDTO;
import com.proyecto.farmacia.DTOs.Proveedor.ProveedorUpdateDTO;
import com.proyecto.farmacia.entity.Medicamento;
import com.proyecto.farmacia.entity.Proveedor;
import com.proyecto.farmacia.service.MedicamentoService;
import com.proyecto.farmacia.service.ProveedorService;
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
@RequestMapping("proveedor")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private MedicamentoService medicamentoService;

    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            List<ProveedorGetDTO> dto = proveedorService.listar().stream()
                    .map(ProveedorMapper::toDTO)
                    .toList();
            return new ResponseEntity<>(new ApiResponse<>("Proveedor", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> obtener(@PathVariable Integer id) {
        try {
            Proveedor proveedor = proveedorService.obtener(id).orElse(null);
            if (proveedor != null) {
                ProveedorGetDTO dto = ProveedorMapper.toDTO(proveedor);
                return new ResponseEntity<>(new ApiResponse<>("Proveedor", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("No existe proveedor", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody ProveedorPostDTO proveedorDTO) {
        try {
            Optional<Proveedor> existente = proveedorService.obtenerPorNombre(proveedorDTO.getNombre());
            if (existente.isPresent()) {
                if (existente.get().getActivo()) {
                    return new ResponseEntity<>(new ApiResponse<>("Proveedor ya existente", existente.get(), false), HttpStatus.CONFLICT);
                } else {
                    return new ResponseEntity<>(new ApiResponse<>("Proveedor existente inactivo, puede reactivarse", existente.get(), false), HttpStatus.CONFLICT);
                }
            }
            List<Medicamento> medicamentos = medicamentoService.obtenerById(proveedorDTO.getMedicamentosId());
            if (medicamentos.size() != proveedorDTO.getMedicamentosId().size()) {
                return new ResponseEntity<>(new ApiResponse<>("Uno o más medicamentos no existen", null, false), HttpStatus.BAD_REQUEST);
            }

            Proveedor proveedor = new Proveedor();
            proveedor.setNombre(proveedorDTO.getNombre());
            proveedor.setEmail(proveedorDTO.getEmail());
            proveedor.setTelefono(proveedorDTO.getTelefono());
            proveedor.setActivo(Boolean.TRUE);
            proveedor.setMedicamentos(medicamentos);
            ProveedorGetDTO dto = ProveedorMapper.toDTO(proveedorService.guardar(proveedor));
            return new ResponseEntity<>(new ApiResponse<>("Proveedor cargado", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody ProveedorUpdateDTO proveedorDTO, @PathVariable Integer id) {
        try {
            Proveedor proveedorDB = proveedorService.obtener(id).orElse(null);
            if (proveedorDB == null) {
                return new ResponseEntity<>(new ApiResponse<>("No se encontro proveedor", null, false), HttpStatus.NOT_FOUND);
            }
            List<Medicamento> medicamentos = medicamentoService.obtenerById(proveedorDTO.getMedicamentosId());
            if (medicamentos.size() != proveedorDTO.getMedicamentosId().size()) {
                return new ResponseEntity<>(new ApiResponse<>("Uno o más medicamentos no existen", null, false), HttpStatus.BAD_REQUEST);
            }
            proveedorDB.setNombre(proveedorDTO.getNombre());
            proveedorDB.setEmail(proveedorDTO.getEmail());
            proveedorDB.setTelefono(proveedorDTO.getTelefono());
            proveedorDB.setMedicamentos(medicamentos);
            ProveedorGetDTO dto = ProveedorMapper.toDTO(proveedorService.guardar(proveedorDB));

            return new ResponseEntity<>(new ApiResponse<>("Proveedor actualizado", dto, true), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            Proveedor proveedor = proveedorService.obtener(id).orElse(null);
            if (proveedor != null) {
                proveedorService.eliminar(proveedor.getId());
                return new ResponseEntity<>(new ApiResponse<>("Proveedor eliminado", ProveedorMapper.toDTO(proveedor), true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("No se pudo eliminar", proveedor, false), HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("reactivar/{id}")
    public ResponseEntity<?> reactivar(@PathVariable Integer id) {
        try {
            Optional<Proveedor> proveedorOpt = proveedorService.obtener(id);
            if (proveedorOpt.isPresent()) {
                Proveedor proveedor = proveedorOpt.get();
                proveedor.setActivo(true);
                ProveedorGetDTO dto = ProveedorMapper.toDTO(proveedorService.guardar(proveedor));
                return new ResponseEntity<>(new ApiResponse<>("Proveedor reactivado", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("Proveedor no encontrado", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
