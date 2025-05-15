package com.proyecto.farmacia.repository;

import com.proyecto.farmacia.entity.DetalleVenta;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Integer> {

    @Override
    public Optional<DetalleVenta> findById(Integer id);
}
