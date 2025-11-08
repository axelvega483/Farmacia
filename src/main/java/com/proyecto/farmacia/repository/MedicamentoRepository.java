package com.proyecto.farmacia.repository;

import com.proyecto.farmacia.entity.Medicamento;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Integer> {

    @Query("SELECT m FROM Medicamento m WHERE m.nombre =:nombre AND m.proveedor.id =:proveedorId AND m.activo = TRUE ")
    Optional<Medicamento> findByNombreAndProveedor(String nombre, Integer proveedorId);

    @Query("SELECT m FROM Medicamento m WHERE  m.activo= TRUE")
    List<Medicamento> findByActivo();

    @Query("SELECT m FROM Medicamento m WHERE m.activo = TRUE AND m.nombre LIKE %:nombre%")
    List<Medicamento> findByNombre(String nombre);
}
