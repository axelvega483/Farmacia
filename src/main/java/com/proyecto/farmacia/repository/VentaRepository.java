package com.proyecto.farmacia.repository;

import com.proyecto.farmacia.entity.Venta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {

    @Query("SELECT v FROM Venta v WHERE v.activo=TRUE AND v.id=:id")
    public Optional<Venta> findByIdActivo(Integer id);

    @Query("SELECT v FROM Venta v WHERE v.activo=TRUE AND v.estado=com.proyecto.farmacia.util.EstadoVenta.FACTURADA")
    public List<Venta> findALlActivo();

    @Override
    public Optional<Venta> findById(Integer id);
}
