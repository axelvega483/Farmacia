package com.proyecto.farmacia.service;

import com.proyecto.farmacia.DTOs.Clientes.ClienteMapper;
import com.proyecto.farmacia.DTOs.Clientes.ClientePostDTO;
import com.proyecto.farmacia.DTOs.Clientes.ClienteUpdateDTO;
import com.proyecto.farmacia.DTOs.Clientes.ClientesGetDTO;
import com.proyecto.farmacia.entity.Cliente;
import com.proyecto.farmacia.interfaz.ClienteInterfaz;
import com.proyecto.farmacia.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
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
        if(findDniActivo(post.dni())){
            throw new EntityExistsException("Cliente ya existente");
        }
        Cliente cliente = mapper.toEntity(post);
        Cliente saved = repo.save(cliente);
        return mapper.toDTO(saved);
    }

    @Override
    public void delete(Integer id) {
        Cliente cliente = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        cliente.setActivo(false);
        repo.save(cliente);
    }

    @Override
    public Optional<ClientesGetDTO> findById(Integer id) {
        Cliente cliente = repo.findById(id)
                .filter(Cliente::isActivo)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));

        return Optional.of(mapper.toDTO(cliente));
    }

    @Override
    public List<ClientesGetDTO> findAll() {
       return mapper.toDTOList(repo.findAll());
    }
    
    public boolean findDniActivo(String dni){
        return repo.existsByDniAndActivoTrue(dni);
    }

    @Override
    public ClientesGetDTO update(Integer id, ClienteUpdateDTO put) {
        Cliente cliente = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
        cliente = mapper.fromUpdateDTO(cliente, put);
        Cliente saved = repo.save(cliente);
        return mapper.toDTO(saved);
    }

}
