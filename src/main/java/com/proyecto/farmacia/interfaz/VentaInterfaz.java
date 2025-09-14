package com.proyecto.farmacia.interfaz;

import com.proyecto.farmacia.DTOs.Ventas.VentaGetDTO;
import com.proyecto.farmacia.DTOs.Ventas.VentaPostDTO;

import java.util.List;
import java.util.Optional;

public interface VentaInterfaz {
    VentaGetDTO create(VentaPostDTO post);

    VentaGetDTO cancel(Integer id);

    Optional<VentaGetDTO> findById(Integer id);


    List<VentaGetDTO> findAll();
}
