package com.proyecto.farmacia.repository;

import com.proyecto.farmacia.entity.Usuario;

import java.util.Optional;

import com.proyecto.farmacia.util.RolUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByDniAndActivoTrue(String dni);

    Integer countByRol(RolUsuario rol);
}
