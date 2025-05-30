package com.proyecto.farmacia.controller;

import com.proyecto.farmacia.DTOs.Clientes.ClienteMapper;
import com.proyecto.farmacia.DTOs.Clientes.ClientePostDTO;
import com.proyecto.farmacia.DTOs.Clientes.ClienteUpdateDTO;
import com.proyecto.farmacia.DTOs.Clientes.ClientesGetDTO;
import com.proyecto.farmacia.entity.Cliente;
import com.proyecto.farmacia.entity.RecetaMedica;
import com.proyecto.farmacia.entity.Venta;
import com.proyecto.farmacia.service.ClienteService;
import com.proyecto.farmacia.service.RecetaMedicaService;
import com.proyecto.farmacia.service.VentasService;
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
@RequestMapping("cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private RecetaMedicaService recetaService;

    @Autowired
    private VentasService ventaService;

    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            List<ClientesGetDTO> dto = clienteService.listar().stream()
                    .map(ClienteMapper::toDTO)
                    .toList();
            return new ResponseEntity<>(new ApiResponse<>("Clientes", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> obtenerId(@PathVariable Integer id) {
        try {
            Cliente cliente = clienteService.obtener(id).orElse(null);
            if (cliente != null) {
                ClientesGetDTO dto = ClienteMapper.toDTO(cliente);
                return new ResponseEntity<>(new ApiResponse<>("Cliente encontrado por id", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("No existe cliente", null, false), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> crearCliente(@Valid @RequestBody ClientePostDTO clienteDTO) {
        try {
            if (clienteService.obtenerDniActivo(clienteDTO.getDni())) {
                return new ResponseEntity<>(new ApiResponse<>("Cliente existente", null, false), HttpStatus.CONFLICT);
            }
            List<RecetaMedica> recetas = recetaService.obtenerById(clienteDTO.getRecetasId());
            if (recetas.size() != clienteDTO.getRecetasId().size()) {
                return new ResponseEntity<>(new ApiResponse<>("Uno o más recetas no existen", null, false), HttpStatus.BAD_REQUEST);
            }

            List<Venta> ventas = ventaService.obtenerById(clienteDTO.getVentasId());
            if (ventas.size() != clienteDTO.getVentasId().size()) {
                return new ResponseEntity<>(new ApiResponse<>("Uno o más ventas no existen", null, false), HttpStatus.BAD_REQUEST);
            }
            Cliente cliente = new Cliente();
            cliente.setActivo(Boolean.TRUE);
            cliente.setDni(clienteDTO.getDni());
            cliente.setEmail(clienteDTO.getEmail());
            cliente.setNombre(cliente.getNombre());
            cliente.setRecetas(recetas);
            cliente.setVentas(ventas);
            ClientesGetDTO dto = ClienteMapper.toDTO(clienteService.guardar(cliente));

            return new ResponseEntity<>(new ApiResponse<>("Cliente cargado", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody ClienteUpdateDTO clienteDTO, @PathVariable Integer id) {
        try {
            Cliente cliente = clienteService.obtener(id).orElse(null);
            if (cliente == null) {
                return new ResponseEntity<>(new ApiResponse<>("No se encontro el cliente", null, false), HttpStatus.NOT_FOUND);
            }
            List<RecetaMedica> recetas = recetaService.obtenerById(clienteDTO.getRecetasId());
            if (recetas.size() != clienteDTO.getRecetasId().size()) {
                return new ResponseEntity<>(new ApiResponse<>("Uno o más recetas no existen", null, false), HttpStatus.BAD_REQUEST);
            }

            List<Venta> ventas = ventaService.obtenerById(clienteDTO.getVentasId());
            if (ventas.size() != clienteDTO.getVentasId().size()) {
                return new ResponseEntity<>(new ApiResponse<>("Uno o más ventas no existen", null, false), HttpStatus.BAD_REQUEST);
            }

            cliente.setActivo(Boolean.TRUE);
            cliente.setDni(clienteDTO.getDni());
            cliente.setEmail(clienteDTO.getEmail());
            cliente.setNombre(cliente.getNombre());
            cliente.setRecetas(recetas);
            cliente.setVentas(ventas);
            ClientesGetDTO dto = ClienteMapper.toDTO(clienteService.guardar(cliente));
            return new ResponseEntity<>(new ApiResponse<>("Cliente actualizado", dto, true), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            Cliente cliente = clienteService.obtener(id).orElse(null);
            if (cliente != null) {
                clienteService.eliminar(cliente.getId());
                ClientesGetDTO dto = ClienteMapper.toDTO(clienteService.guardar(cliente));
                return new ResponseEntity<>(new ApiResponse<>("Cliente eliminado", dto, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("Cliente no encontrado", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
