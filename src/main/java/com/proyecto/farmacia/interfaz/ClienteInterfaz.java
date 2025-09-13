package com.proyecto.farmacia.interfaz;

import com.proyecto.farmacia.DTOs.Clientes.ClientePostDTO;
import com.proyecto.farmacia.DTOs.Clientes.ClienteUpdateDTO;
import com.proyecto.farmacia.DTOs.Clientes.ClientesGetDTO;
import com.proyecto.farmacia.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteInterfaz {
    ClientesGetDTO create(ClientePostDTO post);

    boolean findDniActivo(String dni);

    ClientesGetDTO update(Integer id, ClienteUpdateDTO put);

    void delete(Integer id);

    Optional<ClientesGetDTO> findById(Integer id);

    List<ClientesGetDTO> findAll();

}
