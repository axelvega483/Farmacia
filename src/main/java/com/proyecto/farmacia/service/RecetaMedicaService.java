package com.proyecto.farmacia.service;

import com.proyecto.farmacia.DTOs.RecetaMedica.RecetaMedicaGetDTO;
import com.proyecto.farmacia.DTOs.RecetaMedica.RecetaMedicaMapper;
import com.proyecto.farmacia.DTOs.RecetaMedica.RecetaMedicaPostDTO;
import com.proyecto.farmacia.DTOs.RecetaMedica.RecetaMedicaUptadeDTO;
import com.proyecto.farmacia.entity.Cliente;
import com.proyecto.farmacia.entity.Medicamento;
import com.proyecto.farmacia.entity.RecetaMedica;
import com.proyecto.farmacia.interfaz.RecetaMedicaInterfaz;
import com.proyecto.farmacia.repository.ClienteRepository;
import com.proyecto.farmacia.repository.MedicamentoRepository;
import com.proyecto.farmacia.repository.RecetaMedicaRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RecetaMedicaService implements RecetaMedicaInterfaz {

    @Autowired
    private RecetaMedicaRepository repo;
    @Autowired
    private RecetaMedicaMapper mapper;
    @Autowired
    private ClienteRepository clienteRepo;
    @Autowired
    private MedicamentoRepository medicamentoRepo;


    @Override
    public RecetaMedicaGetDTO create(RecetaMedicaPostDTO post) {
        Optional<Cliente> clienteOpt = clienteRepo.findById(post.cliente());
        if (!clienteOpt.isPresent()) {
            throw new EntityNotFoundException("Cliente inexistente");
        }

        List<Medicamento> medicamentos = medicamentoRepo.findAllById(post.medicamentoIds());
        if (medicamentos.isEmpty()) {
            throw new EntityNotFoundException("Lista de medicamentos vacía");
        }

        if (medicamentos.size() != post.medicamentoIds().size()) {
            throw new EntityNotFoundException("Algunos medicamentos no existen");
        }
        if (post.vigenteHasta().isBefore(LocalDate.now())) {
            throw new EntityNotFoundException("La fecha de vigencia no puede ser anterior a la fecha actual");
        }
        if (post.medicamentoIds().size() > 15) {
            throw new IllegalArgumentException("Una receta no puede tener más de 15 medicamentos");
        }
        Cliente cliente = clienteOpt.get();
        RecetaMedica receta = mapper.toEntity(post, medicamentos, cliente);
        RecetaMedica saved = repo.save(receta);

        return mapper.toDTO(saved);
    }

    @Override
    public RecetaMedicaGetDTO update(Integer id, RecetaMedicaUptadeDTO put) {
        RecetaMedica receta = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Receta no encontrada"));

        Cliente cliente = null;
        List<Medicamento> medicamentos = null;

        if (put.clienteId() != null) {
            cliente = clienteRepo.findById(put.clienteId())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente no existe"));
        }

        if (put.medicamentoIds() != null && !put.medicamentoIds().isEmpty()) {

            if (put.medicamentoIds().size() > 15) {
                throw new IllegalArgumentException("Una receta no puede tener más de 15 medicamentos");
            }

            medicamentos = medicamentoRepo.findAllById(put.medicamentoIds());

            if (medicamentos.size() != put.medicamentoIds().size()) {
                throw new EntityNotFoundException("Algunos medicamentos no existen");
            }
        }
        mapper.updateEntityFromDTO(receta, put, cliente, medicamentos);
        RecetaMedica saved = repo.save(receta);
        return mapper.toDTO(saved);
    }

    @Override
    public Optional<RecetaMedicaGetDTO> findById(Integer id) {
        return repo.findById(id)
                .filter(RecetaMedica::isActivo)
                .map(mapper::toDTO);
    }

    @Override
    public void delete(Integer id) {
        RecetaMedica receta = repo.findById(id)
                .filter(RecetaMedica::isActivo)
                .orElseThrow(() -> new EntityNotFoundException("Receta no encontrada"));

        receta.setActivo(false);

        repo.save(receta);

    }

    @Override
    public List<RecetaMedicaGetDTO> findAll() {
        return mapper.toDTOList(repo.findAll());
    }
}
