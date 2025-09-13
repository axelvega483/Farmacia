package com.proyecto.farmacia.interfaz;

import com.proyecto.farmacia.DTOs.Medicamentos.MedicamentoPostDTO;
import com.proyecto.farmacia.DTOs.Medicamentos.MedicamentoUpdateDTO;
import com.proyecto.farmacia.DTOs.Medicamentos.MedicamentosGetDTO;
import com.proyecto.farmacia.entity.Medicamento;

import java.util.List;
import java.util.Optional;

public interface MedicamentoInterfaz {

    MedicamentosGetDTO create(MedicamentoPostDTO post);

    boolean medicamentoExiste(String nombre, Integer proveedorId);

    void delete(Integer id);

    Optional<MedicamentosGetDTO> findById(Integer id);

    List<MedicamentosGetDTO> findAll();

    List<MedicamentosGetDTO> findByName(String nombre);

    MedicamentosGetDTO update(Integer id, MedicamentoUpdateDTO put);
}
