package com.proyecto.farmacia.interfaz;

import com.proyecto.farmacia.DTOs.Usuarios.UsuarioGetDTO;
import com.proyecto.farmacia.DTOs.Usuarios.UsuarioPostDTO;
import com.proyecto.farmacia.DTOs.Usuarios.UsuarioUpdateDTO;
import com.proyecto.farmacia.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioInterfaz {

    Usuario save(Usuario empleado);

    UsuarioGetDTO create(UsuarioPostDTO post);

    UsuarioGetDTO update(Integer id, UsuarioUpdateDTO put);

    UsuarioGetDTO delete(Integer id);

    Optional<UsuarioGetDTO> findById(Integer id);

    List<UsuarioGetDTO> findAll();

    Boolean existe(String dni);

}
