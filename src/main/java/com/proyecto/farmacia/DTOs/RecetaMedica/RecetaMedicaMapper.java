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
        List<String> medicamentos = recetaMedica.getMedicamentos()
                .stream()
                .filter(medicamento -> medicamento.isActivo())
                .map(Medicamento::getNombre)
                .collect(Collectors.toList());
        return new RecetaMedicaGetDTO(
                recetaMedica.getId(),
                recetaMedica.getMedico(),
                recetaMedica.getFecha(),
                recetaMedica.getVigenteHasta(),
                recetaMedica.isActivo(),
                recetaMedica.getCliente().getNombre(),
                medicamentos
        );
    }

    public RecetaMedica toEntity(RecetaMedicaPostDTO post, List<Medicamento> medicamentos, Cliente cliente) {
        return RecetaMedica.builder()
                .cliente(cliente)
                .fecha(LocalDate.now())
                .medicamentos(medicamentos)
                .medico(post.medico())
                .vigenteHasta(post.vigenteHasta())
                .activo(Boolean.TRUE)
                .build();

    }

    public void updateEntityFromDTO(RecetaMedica receta, RecetaMedicaUptadeDTO put, Cliente cliente, List<Medicamento> medicamentos) {
        if (cliente != null) receta.setCliente(cliente);
        if (put.fecha() != null) receta.setFecha(put.fecha());
        if (put.medico() != null) receta.setMedico(put.medico());
        if (put.vigenteHasta() != null) receta.setVigenteHasta(put.vigenteHasta());
        if (medicamentos != null) receta.setMedicamentos(medicamentos);
        if (put.activo() != null) receta.setActivo(put.activo());
    }

    public List<RecetaMedicaGetDTO> toDTOList(List<RecetaMedica> recetas) {
        return recetas.stream().filter(RecetaMedica::isActivo).map(this::toDTO).toList();
    }
}
