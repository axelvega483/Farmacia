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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
        Optional<Cliente> clienteOpt = clienteRepo.findById(post.getCliente());
        if (!clienteOpt.isPresent()) {
            throw new EntityNotFoundException("Cliente inexistente");
        }

        List<Medicamento> medicamentos = medicamentoRepo.findAllById(post.getMedicamentoIds());
        if (medicamentos.isEmpty()) {
            throw new EntityNotFoundException("Lista de medicamentos vacía");
        }

        if (medicamentos.size() != post.getMedicamentoIds().size()) {
            throw new EntityNotFoundException("Algunos medicamentos no existen");
        }
        if (post.getVigenteHasta().isBefore(LocalDate.now())) {
            throw new EntityNotFoundException("La fecha de vigencia no puede ser anterior a la fecha actual");
        }
        if (post.getMedicamentoIds().size() > 15) {
            throw new IllegalArgumentException("Una receta no puede tener más de 15 medicamentos");
        }
        Cliente cliente = clienteOpt.get();
        RecetaMedica receta = mapper.create(post, medicamentos, cliente);
        RecetaMedica saved = repo.save(receta);

        return mapper.toDTO(saved);
    }

    @Override
    public RecetaMedicaGetDTO update(Integer id, RecetaMedicaUptadeDTO put) {
        RecetaMedica receta = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Receta no encontrada"));

        Cliente cliente = null;
        List<Medicamento> medicamentos = null;

        if (put.getClienteId() != null) {
            cliente = clienteRepo.findById(put.getClienteId())
                    .orElseThrow(() -> new EntityNotFoundException("Cliente no existe"));
            receta.setCliente(cliente);
        }

        if (put.getMedicamentoIds() != null && !put.getMedicamentoIds().isEmpty()) {
            if (put.getMedicamentoIds().size() > 15) {
                throw new IllegalArgumentException("Una receta no puede tener más de 15 medicamentos");
            }

            medicamentos = medicamentoRepo.findAllById(put.getMedicamentoIds());
            if (medicamentos.isEmpty()) {
                throw new EntityNotFoundException("Lista de medicamentos vacía");
            }
            if (medicamentos.size() != put.getMedicamentoIds().size()) {
                throw new EntityNotFoundException("Algunos medicamentos no existen");
            }
            receta.setMedicamentos(medicamentos);
        }
        receta = mapper.update(receta, put, cliente, medicamentos);
        RecetaMedica saved = repo.save(receta);
        return mapper.toDTO(saved);
    }

    @Override
    public Optional<RecetaMedicaGetDTO> findById(Integer id) {
        Optional<RecetaMedica> receta = repo.findById(id).filter(RecetaMedica::getActivo);
        if (receta.isPresent()) {
            RecetaMedicaGetDTO dto = mapper.toDTO(receta.get());
            return Optional.of(dto);
        }

        return Optional.empty();
    }

    @Override
    public void delete(Integer id) {
        Optional<RecetaMedica> recetaOptional = repo.findById(id);
        if (recetaOptional.isPresent()) {
            RecetaMedica recetaMedica = recetaOptional.get();
            recetaMedica.setActivo(Boolean.FALSE);
            repo.save(recetaMedica);
        }

    }

    @Override
    public List<RecetaMedicaGetDTO> findAll() {
        List<RecetaMedica> recetas = repo.findAll();
        List<RecetaMedicaGetDTO> dtos = new ArrayList<>();
        for (RecetaMedica receta : recetas) {
            RecetaMedicaGetDTO dto = mapper.toDTO(receta);
            dtos.add(dto);
        }
        return dtos;
    }

    public List<RecetaMedica> obtenerById(List<Integer> id) {
        return repo.findAllById(id);
    }
}
