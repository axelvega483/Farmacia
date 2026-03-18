package com.proyecto.farmacia.service;

import com.proyecto.farmacia.DTOs.Usuarios.*;
import com.proyecto.farmacia.entity.Usuario;
import com.proyecto.farmacia.interfaz.UsuarioInterfaz;
import com.proyecto.farmacia.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

import com.proyecto.farmacia.util.RolUsuario;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UsuarioService implements UsuarioInterfaz {

    @Autowired
    private UsuarioRepository repo;

    @Autowired
    private UsuarioMapper mapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuario save(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return repo.save(usuario);
    }

    @Override
    public UsuarioGetDTO create(UsuarioPostDTO post) {
        if (existe(post.dni())) {
            throw new EntityExistsException("El usuario ya existe");
        }
        Usuario usuario = mapper.toEntity(post);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        Usuario saved = repo.save(usuario);
        return mapper.toDTO(saved);
    }

    @Override
    public UsuarioGetDTO update(Integer id, UsuarioUpdateDTO put) {
        Usuario usuario = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        mapper.fromUpdateDTO(usuario, put);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return mapper.toDTO(repo.save(usuario));
    }

    @Override
    public UsuarioGetDTO actualizarRol(Integer id, UsuarioRolDTO dto) {
        if (dto.rol() == null) {
            throw new IllegalArgumentException("El rol es obligatorio");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        Usuario usuarioLogueado = repo.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Usuario autenticado no encontrado"));
        if (usuarioLogueado.getId().equals(id)) {
            throw new IllegalStateException("No podés cambiar tu propio rol");
        }
        Usuario usuario = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (usuario.getRol() == RolUsuario.ADMIN && dto.rol() != RolUsuario.ADMIN) {
            long admins = repo.countByRol(RolUsuario.ADMIN);
            if (admins <= 1) {
                throw new IllegalStateException("Debe existir al menos un ADMIN");
            }
        }

        usuario.setRol(dto.rol());
        repo.save(usuario);

        return mapper.toDTO(usuario);
    }


    @Override
    public UsuarioGetDTO delete(Integer id) {
        Usuario usuario = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        usuario.setActivo(false);
        return mapper.toDTO(repo.save(usuario));
    }

    @Override
    public Optional<UsuarioGetDTO> findById(Integer id) {
        return repo.findById(id).filter(Usuario::isActivo).map(mapper::toDTO);
    }


    @Override
    public List<UsuarioGetDTO> findAll() {
        return mapper.toDTOList(repo.findAll());
    }

    @Override
    public Boolean existe(String dni) {
        return repo.existsByDniAndActivoTrue(dni);
    }

}
