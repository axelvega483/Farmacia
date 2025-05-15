package com.proyecto.farmacia.DTOs.RecetaMedica;

import com.proyecto.farmacia.entity.Medicamento;
import com.proyecto.farmacia.entity.RecetaMedica;
import java.util.List;
import java.util.stream.Collectors;

public class RecetaMedicaMapper {

    public static RecetaMedicaGetDTO toDTO(RecetaMedica recetaMedica) {
        RecetaMedicaGetDTO dto = new RecetaMedicaGetDTO();

        dto.setId(recetaMedica.getId());
        dto.setClienteNombre(recetaMedica.getCliente().getNombre());
        dto.setFecha(recetaMedica.getFecha());
        dto.setMedico(recetaMedica.getMedico());
        dto.setVigenteHasta(recetaMedica.getVigenteHasta());
        dto.setActivo(recetaMedica.getActivo());

        List<String> medicamentos = recetaMedica.getMedicamentos().stream()
                .map(Medicamento::getNombre)
                .collect(Collectors.toList());
        dto.setMedicamentosNombres(medicamentos);
        return dto;
    }

}
