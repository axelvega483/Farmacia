package com.proyecto.farmacia.interfaz;

import com.proyecto.farmacia.DTOs.Proveedor.ProveedorGetDTO;
import com.proyecto.farmacia.DTOs.Proveedor.ProveedorPostDTO;
import com.proyecto.farmacia.DTOs.Proveedor.ProveedorUpdateDTO;
import com.proyecto.farmacia.entity.Proveedor;
import java.util.List;
import java.util.Optional;

public interface ProveedorInterfaz {

    ProveedorGetDTO create(ProveedorPostDTO post);

    ProveedorGetDTO update(Integer id, ProveedorUpdateDTO put);

     void delete(Integer id);

   Optional<ProveedorGetDTO> findById(Integer id);

     List<ProveedorGetDTO> findAll();
}
