package com.proyecto.farmacia.controller;

import com.proyecto.farmacia.DTOs.Clientes.ClientePostDTO;
import com.proyecto.farmacia.DTOs.Clientes.ClienteUpdateDTO;
import com.proyecto.farmacia.DTOs.Clientes.ClientesGetDTO;
import com.proyecto.farmacia.interfaz.ClienteInterfaz;
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
    private ClienteInterfaz clienteService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<ClientesGetDTO> dto = clienteService.findAll();
            return new ResponseEntity<>(new ApiResponse<>("Clientes", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            ClientesGetDTO cliente = clienteService.findById(id).orElse(null);
            if (cliente != null) {

                return new ResponseEntity<>(new ApiResponse<>("Cliente encontrado por id", cliente, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("No existe cliente", null, false), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ClientePostDTO clienteDTO) {
        try {
            ClientesGetDTO dto = clienteService.create(clienteDTO);
            return new ResponseEntity<>(new ApiResponse<>("Cliente cargado", dto, true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@Valid @RequestBody ClienteUpdateDTO clienteDTO, @PathVariable Integer id) {
        try {
           ClientesGetDTO dto = clienteService.update(id,clienteDTO);
            return new ResponseEntity<>(new ApiResponse<>("Cliente actualizado", dto, true), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            ClientesGetDTO cliente = clienteService.findById(id).orElse(null);
            if (cliente != null) {
                clienteService.delete(cliente.getId());
                return new ResponseEntity<>(new ApiResponse<>("Cliente eliminado", null, true), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse<>("Cliente no encontrado", null, false), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>("Error: " + e.getMessage(), null, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
