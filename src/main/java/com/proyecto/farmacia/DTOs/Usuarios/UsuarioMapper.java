package com.proyecto.farmacia.DTOs.Usuarios;

import com.proyecto.farmacia.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {

    public UsuarioGetDTO toDTO(Usuario usuario) {
        List<UsuarioVentaDTO> ventas = usuario.getVentas().stream()
                .filter(venta -> venta.isActivo())
                .map(venta -> new UsuarioVentaDTO(
                        venta.getId(),
                        venta.getFecha(),
                        venta.getTotal()))
                .collect(Collectors.toList());

        return new UsuarioGetDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getDni(),
                usuario.getRol(),
                usuario.isActivo(),
                ventas

        );
    }

    public Usuario toEntity(UsuarioPostDTO post) {
        return Usuario.builder()
                .dni(post.dni())
                .email(post.email())
                .nombre(post.nombre())
                .password(post.password())
                .rol(post.rol())
                .activo(true)
                .ventas(Collections.emptyList())
                .build();
    }

    public void fromUpdateDTO(Usuario usuario, UsuarioUpdateDTO put) {
        if (put.dni() != null) usuario.setDni(put.dni());
        if (put.email() != null) usuario.setEmail(put.email());
        if (put.nombre() != null) usuario.setNombre(put.nombre());
        if (put.password() != null) usuario.setPassword(put.password());
        if (put.rol() != null) usuario.setRol(put.rol());
        if (put.activo() != null) usuario.setActivo(put.activo());
    }

    public List<UsuarioGetDTO> toDTOList(List<Usuario> usuarios) {
        return usuarios.stream().filter(Usuario::isActivo).map(this::toDTO).toList();
    }
}
