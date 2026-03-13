package com.proyecto.farmacia.repository;

import com.proyecto.farmacia.entity.Proveedor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
    Optional<Proveedor> findByNombreIgnoreCase(String nombre);
}
