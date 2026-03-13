package com.proyecto.farmacia.repository;

import com.proyecto.farmacia.entity.Medicamento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Integer> {
    boolean  existsByNombreAndProveedorIdAndActivoTrue(String nombre, Integer proveedorId);

    List<Medicamento> findByNombreContainingIgnoreCaseAndActivoTrue(String nombre);
}
