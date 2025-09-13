package com.proyecto.farmacia.service;

import com.proyecto.farmacia.DTOs.Empleados.EmpleadoGetDTO;
import com.proyecto.farmacia.DTOs.Empleados.EmpleadoMapper;
import com.proyecto.farmacia.DTOs.Empleados.EmpleadoPostDTO;
import com.proyecto.farmacia.DTOs.Empleados.EmpleadoUpdateDTO;
import com.proyecto.farmacia.entity.Empleado;
import com.proyecto.farmacia.interfaz.EmpleadoInterfaz;
import com.proyecto.farmacia.repository.EmpleadoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmpleadoService implements EmpleadoInterfaz {

    @Autowired
    private EmpleadoRepository repo;

    @Autowired
    private EmpleadoMapper mapper;

    @Override
    public Empleado save(Empleado empleado) {
        return repo.save(empleado);
    }

    @Override
    public EmpleadoGetDTO create(EmpleadoPostDTO post) {
        if (existe(post.getDni())) {
            throw new EntityExistsException("El empleado ya existe");
        }
        Empleado empleado = mapper.crearte(post);
        Empleado saved = repo.save(empleado);
        return mapper.toDTO(saved);
    }

    @Override
    public EmpleadoGetDTO update(Integer id, EmpleadoUpdateDTO put) {
        Empleado empleado = repo.findById(id).orElse(null);
        if (empleado == null) {
            throw new EntityExistsException("El Usuario no existe");
        }
        empleado = mapper.update(empleado, put);
        Empleado saved = repo.save(empleado);
        return mapper.toDTO(saved);
    }

    @Override
    public EmpleadoGetDTO delete(Integer id) {
        Optional<Empleado> empleadoOptional = repo.findById(id);
        if (empleadoOptional.isPresent()) {
            Empleado empleado = empleadoOptional.get();
            empleado.setActivo(Boolean.FALSE);
            repo.save(empleado);
            return mapper.toDTO(empleado);
        }
        throw new EntityNotFoundException("Empleado no encontrado");
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmpleadoGetDTO> findById(Integer id) {
        Optional<Empleado> empleado = repo.findById(id).filter(Empleado::getActivo);
        if (empleado.isPresent()) {
            EmpleadoGetDTO dto = mapper.toDTO(empleado.get());
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmpleadoGetDTO> findAll() {
        List<Empleado> empleados = repo.findAll();
        List<EmpleadoGetDTO> dtos = new ArrayList<>();
        for (Empleado empleado : empleados) {
            EmpleadoGetDTO dto = mapper.toDTO(empleado);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public Boolean existe(String dni) {
        return repo.findByDniAndActivo(dni).isPresent();
    }

    @Override
    public Optional<Empleado> findByCorreoAndPassword(String email, String password) {
        return repo.findByCorreoAndPassword(email, password);
    }

}
