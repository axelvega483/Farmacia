package com.proyecto.farmacia.interfaz;

import com.proyecto.farmacia.DTOs.RecetaMedica.RecetaMedicaGetDTO;
import com.proyecto.farmacia.DTOs.RecetaMedica.RecetaMedicaPostDTO;
import com.proyecto.farmacia.DTOs.RecetaMedica.RecetaMedicaUptadeDTO;

import java.util.List;
import java.util.Optional;

public interface RecetaMedicaInterfaz {
    RecetaMedicaGetDTO create(RecetaMedicaPostDTO post);

    RecetaMedicaGetDTO update(Integer id, RecetaMedicaUptadeDTO put);

    Optional<RecetaMedicaGetDTO> findById(Integer id);

    void delete(Integer id);

    List<RecetaMedicaGetDTO> findAll();
}
