package com.proyecto.farmacia.DTOs.RecetaMedica;

import com.proyecto.farmacia.entity.Cliente;
import com.proyecto.farmacia.entity.Medicamento;
import com.proyecto.farmacia.entity.RecetaMedica;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecetaMedicaMapper {

    public RecetaMedicaGetDTO toDTO(RecetaMedica recetaMedica) {
        RecetaMedicaGetDTO dto = new RecetaMedicaGetDTO();

        dto.setId(recetaMedica.getId());
        dto.setClienteNombre(recetaMedica.getCliente().getNombre());
        dto.setFecha(recetaMedica.getFecha());
        dto.setMedico(recetaMedica.getMedico());
        dto.setVigenteHasta(recetaMedica.getVigenteHasta());
        dto.setActivo(recetaMedica.getActivo());

        List<String> medicamentos = recetaMedica.getMedicamentos().stream().map(Medicamento::getNombre).collect(Collectors.toList());
        dto.setMedicamentosNombres(medicamentos);
        return dto;
    }

    public RecetaMedica create(RecetaMedicaPostDTO post, List<Medicamento> medicamentos, Cliente cliente) {
        RecetaMedica receta = new RecetaMedica();
        receta.setCliente(cliente);
        receta.setFecha(LocalDate.now());
        receta.setMedicamentos(medicamentos);
        receta.setMedico(post.getMedico());
        receta.setVigenteHasta(post.getVigenteHasta());
        receta.setActivo(Boolean.TRUE);
        return receta;
    }

    public RecetaMedica update(RecetaMedica receta, RecetaMedicaUptadeDTO put, Cliente cliente, List<Medicamento> medicamentos) {
        if (cliente != null) receta.setCliente(cliente);
        if (put.getFecha() != null) receta.setFecha(put.getFecha());
        if (put.getMedico() != null) receta.setMedico(put.getMedico());
        if (put.getVigenteHasta() != null) receta.setVigenteHasta(put.getVigenteHasta());
        if (medicamentos != null) receta.setMedicamentos(medicamentos);
        if (put.getActivo() != null) receta.setActivo(put.getActivo());
        return receta;
    }

}
