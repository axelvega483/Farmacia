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
        if (medicamentoExiste(post.getNombre(), post.getProveedor().getId())) {
            throw new EntityExistsException("El medicamento ya existe");
        }
        Medicamento medicamento = mapper.create(post);
        Medicamento saved = repo.save(medicamento);
        return mapper.toDTO(saved);
    }

    @Override
    public void delete(Integer id) {
        Optional<Medicamento> medicamento = repo.findById(id);
        if (medicamento.isPresent()) {
            Medicamento m = medicamento.get();
            m.setActivo(Boolean.FALSE);
            repo.save(m);
        }
    }

    @Override
    public Optional<MedicamentosGetDTO> findById(Integer id) {
        Optional<Medicamento> medicamento = repo.findById(id).filter(Medicamento::getActivo);
        if (medicamento.isPresent()) {
            MedicamentosGetDTO dto = mapper.toDTO(medicamento.get());
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    @Override
    public List<MedicamentosGetDTO> findAll() {
        List<Medicamento> medicamentos = repo.findAll();
        List<MedicamentosGetDTO> dtos = new ArrayList<>();
        for (Medicamento medicamento : medicamentos) {
            MedicamentosGetDTO dto = mapper.toDTO(medicamento);
            dtos.add(dto);
        }
        return dtos;
    }

    public boolean medicamentoExiste(String nombre, Integer proveedorId) {
        return repo.findByNombreAndProveedor(nombre, proveedorId).isPresent();
    }

    public List<MedicamentosGetDTO> findByName(String nombre) {
        List<Medicamento> medicamentos = repo.findByNombre(nombre);
        List<MedicamentosGetDTO> dtos = new ArrayList<>();
        for (Medicamento medicamento : medicamentos) {
            MedicamentosGetDTO dto = mapper.toDTO(medicamento);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public MedicamentosGetDTO update(Integer id, MedicamentoUpdateDTO put) {
        Medicamento medicamento = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medicamento no encontrado"));

        Proveedor proveedor = null;
        if (put.getProveedor() != null && put.getProveedor().getId() != null) {
            proveedor = proveedorRepository.findById(put.getProveedor().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));
        }
        medicamento = mapper.update(medicamento, put, proveedor);
        Medicamento saved = repo.save(medicamento);
        return mapper.toDTO(saved);
    }
}
