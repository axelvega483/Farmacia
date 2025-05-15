package com.proyecto.farmacia.repository;

import com.proyecto.farmacia.entity.Proveedor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

    @Query("SELECT p FROM Proveedor p WHERE p.activo = TRUE")
    public List<Proveedor> findByActivo();

    @Query("SELECT p FROM Proveedor p WHERE LOWER(p.nombre) = LOWER(:nombre)")
    public Optional<Proveedor> findByNombre(String nombre);
}
