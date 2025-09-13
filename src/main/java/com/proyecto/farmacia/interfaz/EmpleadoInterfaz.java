package com.proyecto.farmacia.interfaz;

import com.proyecto.farmacia.DTOs.Empleados.EmpleadoGetDTO;
import com.proyecto.farmacia.DTOs.Empleados.EmpleadoPostDTO;
import com.proyecto.farmacia.DTOs.Empleados.EmpleadoUpdateDTO;
import com.proyecto.farmacia.entity.Empleado;

import java.util.List;
import java.util.Optional;

public interface EmpleadoInterfaz {

    Empleado save(Empleado empleado);

    EmpleadoGetDTO create(EmpleadoPostDTO post);

    EmpleadoGetDTO update(Integer id, EmpleadoUpdateDTO put);

    EmpleadoGetDTO delete(Integer id);

    Optional<EmpleadoGetDTO> findById(Integer id);

    List<EmpleadoGetDTO> findAll();

    Boolean existe(String dni);

    Optional<Empleado> findByCorreoAndPassword(String email, String password);
}
