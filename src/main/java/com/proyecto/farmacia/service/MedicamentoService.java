package com.proyecto.farmacia.service;

import com.proyecto.farmacia.DTOs.Medicamentos.MedicamentoMapper;
import com.proyecto.farmacia.DTOs.Medicamentos.MedicamentoPostDTO;
import com.proyecto.farmacia.DTOs.Medicamentos.MedicamentoUpdateDTO;
import com.proyecto.farmacia.DTOs.Medicamentos.MedicamentosGetDTO;
import com.proyecto.farmacia.entity.Medicamento;
import com.proyecto.farmacia.entity.Proveedor;
import com.proyecto.farmacia.interfaz.MedicamentoInterfaz;
import com.proyecto.farmacia.repository.MedicamentoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.proyecto.farmacia.repository.ProveedorRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicamentoService implements MedicamentoInterfaz {

    @Autowired
    private MedicamentoRepository repo;
    @Autowired
    private MedicamentoMapper mapper;
    @Autowired
    private ProveedorRepository proveedorRepository;

    @Override
    public MedicamentosGetDTO create(MedicamentoPostDTO post) {
        if (medicamentoExiste(post.nombre(), post.proveedorId())) {
            throw new EntityExistsException("El medicamento ya existe");
        }
        Proveedor proveedor = proveedorRepository
                .findById(post.proveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        Medicamento medicamento = mapper.toEntity(post,proveedor);
        Medicamento saved = repo.save(medicamento);
        return mapper.toDTO(saved);
    }

    @Override
    public void delete(Integer id) {
        Medicamento medicamento = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medicamento no encontrado"));

        medicamento.setActivo(false);
        repo.save(medicamento);
    }

    @Override
    public Optional<MedicamentosGetDTO> findById(Integer id) {
        Medicamento medicamento = repo.findById(id)
                .filter(Medicamento::isActivo)
                .orElseThrow(() -> new EntityNotFoundException("Medicamento no encontrado"));

        return Optional.of(mapper.toDTO(medicamento));
    }

    @Override
    public List<MedicamentosGetDTO> findAll() {
        return mapper.toDTOList(repo.findAll());
    }

    public boolean medicamentoExiste(String nombre, Integer proveedorId) {
        return repo.existsByNombreAndProveedorIdAndActivoTrue(nombre, proveedorId);
    }

    public List<MedicamentosGetDTO> findByName(String nombre) {
        return repo
                .findByNombreContainingIgnoreCaseAndActivoTrue(nombre)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public MedicamentosGetDTO update(Integer id, MedicamentoUpdateDTO put) {
        Medicamento medicamento = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medicamento no encontrado"));
        Proveedor proveedor = null;
        if (put.proveedor() != null && put.proveedor().getId() != null) {
            proveedor = proveedorRepository.findById(put.proveedor().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));
        }
        medicamento = mapper.updateEntityFromDTO(medicamento, put, proveedor);
        Medicamento saved = repo.save(medicamento);
        return mapper.toDTO(saved);
    }
}
