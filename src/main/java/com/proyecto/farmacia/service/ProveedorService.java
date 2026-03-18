package com.proyecto.farmacia.service;

import com.proyecto.farmacia.DTOs.Proveedor.ProveedorGetDTO;
import com.proyecto.farmacia.DTOs.Proveedor.ProveedorMapper;
import com.proyecto.farmacia.DTOs.Proveedor.ProveedorPostDTO;
import com.proyecto.farmacia.DTOs.Proveedor.ProveedorUpdateDTO;
import com.proyecto.farmacia.entity.Proveedor;
import com.proyecto.farmacia.interfaz.ProveedorInterfaz;
import com.proyecto.farmacia.repository.ProveedorRepository;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProveedorService implements ProveedorInterfaz {

    @Autowired
    private ProveedorRepository repo;
    @Autowired
    private ProveedorMapper mapper;

    @Override
    public ProveedorGetDTO create(ProveedorPostDTO post) {
        if (findByName(post.nombre()).isPresent()) {
            throw new EntityExistsException("Proveedor existente");
        }
        Proveedor proveedor = mapper.toEntity(post);
        Proveedor saved = repo.save(proveedor);
        return mapper.toDTO(saved);
    }

    @Override
    public ProveedorGetDTO update(Integer id, ProveedorUpdateDTO put) {
        Proveedor proveedor = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));

        mapper.updateEntityFromDTO(proveedor, put);
        Proveedor saved = repo.save(proveedor);
        return mapper.toDTO(saved);
    }

    @Override
    public ProveedorGetDTO delete(Integer id) {

        Proveedor proveedor = repo.findById(id)
                .filter(Proveedor::isActivo)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));

        proveedor.setActivo(false);
        repo.save(proveedor);
        return mapper.toDTO(proveedor);
    }

    @Override
    public Optional<ProveedorGetDTO> findById(Integer id) {
        return repo.findById(id)
                .filter(Proveedor::isActivo)
                .map(mapper::toDTO);
    }

    @Override
    public List<ProveedorGetDTO> findAll() {
       return mapper.toDTOList(repo.findAll());
    }

    @Override
    public Optional<ProveedorGetDTO> findByName(String nombre) {
        return repo.findByNombreIgnoreCase(nombre)
                .map(mapper::toDTO);
    }

}
