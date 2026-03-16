package com.proyecto.farmacia.service;

import com.proyecto.farmacia.DTOs.Usuarios.UsuarioGetDTO;
import com.proyecto.farmacia.DTOs.Usuarios.UsuarioMapper;
import com.proyecto.farmacia.DTOs.Usuarios.UsuarioPostDTO;
import com.proyecto.farmacia.DTOs.Usuarios.UsuarioUpdateDTO;
import com.proyecto.farmacia.entity.Usuario;
import com.proyecto.farmacia.interfaz.UsuarioInterfaz;
import com.proyecto.farmacia.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
