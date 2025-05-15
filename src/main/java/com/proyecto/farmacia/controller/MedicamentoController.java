package com.proyecto.farmacia.controller;

import com.proyecto.farmacia.DTOs.Medicamentos.MedicamentoMapper;
import com.proyecto.farmacia.DTOs.Medicamentos.MedicamentoPostDTO;
import com.proyecto.farmacia.DTOs.Medicamentos.MedicamentoUpdateDTO;
import com.proyecto.farmacia.DTOs.Medicamentos.MedicamentosGetDTO;
import com.proyecto.farmacia.entity.DetalleVenta;
import com.proyecto.farmacia.entity.Medicamento;
import com.proyecto.farmacia.service.DetalleVentaService;
import com.proyecto.farmacia.service.MedicamentoService;
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
    private MedicamentoService medicamentoService;

    @Autowired
    private DetalleVentaService detalleService;

    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            List<MedicamentosGetDTO> dto = medicamentoService.listar()
                    .stream().map(MedicamentoMapper::toDTO).toList();
            return new ResponseEntity<>(new ApiResponse<>("Medicamentos", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("nombre/{nombre}")
    public ResponseEntity<?> buscarPorNombre(@PathVariable String nombre) {
        try {
            List<Medicamento> medicamentos = medicamentoService.buscarPorNombre(nombre);
            if (medicamentos != null && !medicamentos.isEmpty()) {
                List<MedicamentosGetDTO> dto = medicamentos.stream()
                        .map(MedicamentoMapper::toDTO).toList();
                return new ResponseEntity<>(new ApiResponse<>("Medicamentos encontrados por nombre", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("No se encontraron coincidencia", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> obtenerId(@PathVariable Integer id) {
        try {
            Medicamento medicamento = medicamentoService.obtener(id).orElse(null);
            if (medicamento != null) {
                MedicamentosGetDTO dto = MedicamentoMapper.toDTO(medicamento);
                return new ResponseEntity<>(new ApiResponse<>("Medicamento", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("Medicamento inexistente", null, false), HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> cargar(@Valid @RequestBody MedicamentoPostDTO medicamentoDTO) {
        try {
            if (medicamentoService.medicamentoExiste(medicamentoDTO.getNombre(), medicamentoDTO.getProveedor().getId())) {
                return new ResponseEntity<>(new ApiResponse<>("Medicamento ya existente", medicamentoDTO, false), HttpStatus.CONFLICT);
            } else {

                List<DetalleVenta> detalles = detalleService.obtenerById(medicamentoDTO.getDetalleVentasID());
                if (detalles.size() != medicamentoDTO.getDetalleVentasID().size()) {
                    return new ResponseEntity<>(new ApiResponse<>("Uno o más detalle no existen", null, false), HttpStatus.BAD_REQUEST);
                }
                Medicamento medicamento = new Medicamento();
                medicamento.setActivo(Boolean.TRUE);
                medicamento.setDescripcion(medicamentoDTO.getDescripcion());
                medicamento.setDetalleVentas(detalles);
                medicamento.setFechaVencimiento(medicamentoDTO.getFechaVencimiento());
                medicamento.setNombre(medicamentoDTO.getNombre());
                medicamento.setPrecio(medicamentoDTO.getPrecio());
                medicamento.setProveedor(medicamentoDTO.getProveedor());
                medicamento.setRecetaRequerida(medicamentoDTO.getRecetaRequerida());
                medicamento.setStock(medicamentoDTO.getStock());
                MedicamentosGetDTO dto = MedicamentoMapper.toDTO(medicamentoService.guardar(medicamento));
                return new ResponseEntity<>(new ApiResponse<>("Medicamento cargado", dto, true), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody MedicamentoUpdateDTO medicamentoDTO, @PathVariable Integer id) {
        try {
            Medicamento medicamento = medicamentoService.obtener(id).orElse(null);
            if (medicamento == null) {
                return new ResponseEntity<>(new ApiResponse<>("No se pudo actualizar", null, false), HttpStatus.NOT_MODIFIED);
            }

            String nombreNuevo = medicamentoDTO.getNombre() != null ? medicamentoDTO.getNombre() : medicamento.getNombre();
            Integer proveedorIdNuevo = (medicamentoDTO.getProveedor() != null && medicamentoDTO.getProveedor().getId() != null)
                    ? medicamentoDTO.getProveedor().getId()
                    : (medicamento.getProveedor() != null ? medicamento.getProveedor().getId() : null);

            boolean nombreCambio = !nombreNuevo.equalsIgnoreCase(medicamento.getNombre());
            boolean proveedorCambio = proveedorIdNuevo != null && !proveedorIdNuevo.equals(medicamento.getProveedor().getId());

            if ((nombreCambio || proveedorCambio) && medicamentoService.medicamentoExiste(nombreNuevo, proveedorIdNuevo)) {
                return new ResponseEntity<>(new ApiResponse<>("Ya existente con ese nombre y proveedor", medicamentoDTO, false), HttpStatus.CONFLICT);
            }
            List<DetalleVenta> detalles = detalleService.obtenerById(medicamentoDTO.getDetalleVentasID());
            if (detalles.size() != medicamentoDTO.getDetalleVentasID().size()) {
                return new ResponseEntity<>(new ApiResponse<>("Uno o más detalle no existen", null, false), HttpStatus.BAD_REQUEST);
            }
            medicamento.setActivo(Boolean.TRUE);
            medicamento.setDescripcion(medicamentoDTO.getDescripcion());
            medicamento.setDetalleVentas(detalles);
            medicamento.setFechaVencimiento(medicamentoDTO.getFechaVencimiento());
            medicamento.setNombre(medicamentoDTO.getNombre());
            medicamento.setPrecio(medicamentoDTO.getPrecio());
            medicamento.setProveedor(medicamentoDTO.getProveedor());
            medicamento.setRecetaRequerida(medicamentoDTO.getRecetaRequerida());
            medicamento.setStock(medicamentoDTO.getStock());
            MedicamentosGetDTO dto = MedicamentoMapper.toDTO(medicamentoService.guardar(medicamento));
            return new ResponseEntity<>(new ApiResponse<>("Medicamento actualizado", dto, true), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            Medicamento medicamento = medicamentoService.obtener(id).orElse(null);
            if (medicamento != null) {
                medicamentoService.eliminar(medicamento.getId());
                MedicamentosGetDTO dto = MedicamentoMapper.toDTO(medicamentoService.guardar(medicamento));
                return new ResponseEntity<>(new ApiResponse<>("Medicamento eliminado", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("No se pudo eliminar", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
