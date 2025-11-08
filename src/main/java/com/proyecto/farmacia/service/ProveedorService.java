package com.proyecto.farmacia.service;

import com.proyecto.farmacia.DTOs.Proveedor.ProveedorGetDTO;
import com.proyecto.farmacia.DTOs.Proveedor.ProveedorMapper;
import com.proyecto.farmacia.DTOs.Proveedor.ProveedorPostDTO;
import com.proyecto.farmacia.DTOs.Proveedor.ProveedorUpdateDTO;
import com.proyecto.farmacia.entity.Proveedor;
import com.proyecto.farmacia.interfaz.ProveedorInterfaz;
import com.proyecto.farmacia.repository.ProveedorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityExistsException;
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
        if (findByName(post.getNombre()).isPresent()) {
            throw new EntityExistsException("Proveedor existente");
        }
        Proveedor proveedor = mapper.create(post);
        Proveedor saved = repo.save(proveedor);
        return mapper.toDTO(saved);
    }

    @Override
    public ProveedorGetDTO update(Integer id, ProveedorUpdateDTO put) {
        Proveedor proveedor = repo.findById(id).orElse(null);
        if (proveedor == null) {
            throw new EntityExistsException("Proveedor no encontrado");
        }
        proveedor = mapper.update(proveedor, put);
        Proveedor saved = repo.save(proveedor);
        return mapper.toDTO(saved);
    }

    @Override
    public void delete(Integer id) {
        Optional<Proveedor> proveedorOptional = repo.findById(id);
        if (proveedorOptional.isPresent()) {
            Proveedor proveedor = proveedorOptional.get();
            proveedor.setActivo(Boolean.FALSE);
            repo.save(proveedor);
        }
    }

    @Override
    public Optional<ProveedorGetDTO> findById(Integer id) {
        Optional<Proveedor> proveedor = repo.findById(id).filter(Proveedor::getActivo);
        if (proveedor.isPresent()) {
            ProveedorGetDTO dto = mapper.toDTO(proveedor.get());
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    @Override
    public List<ProveedorGetDTO> findAll() {
        List<Proveedor> proveedores = repo.findAll();
        List<ProveedorGetDTO> dtos = new ArrayList<>();
        for (Proveedor proveedor : proveedores) {
            ProveedorGetDTO dto = mapper.toDTO(proveedor);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public Optional<ProveedorGetDTO> findByName(String nombre) {
        Optional<Proveedor> proveedor = repo.findByName(nombre).filter(Proveedor::getActivo);
        if (proveedor.isPresent()) {
            ProveedorGetDTO dto = mapper.toDTO(proveedor.get());
            return Optional.of(dto);
        }
        return Optional.empty();
    }

}
