package com.proyecto.farmacia.service;

import com.proyecto.farmacia.DTOs.Clientes.ClienteMapper;
import com.proyecto.farmacia.DTOs.Clientes.ClientePostDTO;
import com.proyecto.farmacia.DTOs.Clientes.ClienteUpdateDTO;
import com.proyecto.farmacia.DTOs.Clientes.ClientesGetDTO;
import com.proyecto.farmacia.entity.Cliente;
import com.proyecto.farmacia.interfaz.ClienteInterfaz;
import com.proyecto.farmacia.repository.ClienteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService implements ClienteInterfaz {

    @Autowired
    private ClienteRepository repo;
    @Autowired
    private ClienteMapper mapper;


    @Override
    public ClientesGetDTO create(ClientePostDTO post) {
        if(findDniActivo(post.getDni())){
            throw new EntityExistsException("Cliente ya existente");
        }
        Cliente cliente = mapper.create(post);
        Cliente saved = repo.save(cliente);
        return mapper.toDTO(saved);
    }

    @Override
    public void delete(Integer id) {
        Optional<Cliente> optionalCliente = repo.findById(id);
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            cliente.setActivo(Boolean.FALSE);
            repo.save(cliente);
        }
    }

    @Override
    public Optional<ClientesGetDTO> findById(Integer id) {
        Optional<Cliente> cliente = repo.findById(id).filter(Cliente::getActivo);
        if (cliente.isPresent()) {
            ClientesGetDTO dto = mapper.toDTO(cliente.get());
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    @Override
    public List<ClientesGetDTO> findAll() {
        List<Cliente> clientes = repo.findAll();
        List<ClientesGetDTO> dtos = new ArrayList<>();
        for (Cliente cliente : clientes) {
            ClientesGetDTO dto = mapper.toDTO(cliente);
            dtos.add(dto);
        }
        return dtos;
    }
    
    public boolean findDniActivo(String dni){
        return repo.findByDniAndActivo(dni).isPresent();
    }

    @Override
    public ClientesGetDTO update(Integer id, ClienteUpdateDTO put) {
        Cliente cliente = repo.findById(id).orElse(null);
        if (cliente == null) {
            throw new EntityExistsException("El Cliente no existe");
        }
        cliente = mapper.update(cliente, put);
        Cliente saved = repo.save(cliente);
        return mapper.toDTO(saved);
    }

}
