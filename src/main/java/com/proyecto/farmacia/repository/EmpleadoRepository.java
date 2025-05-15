package com.proyecto.farmacia.repository;

import com.proyecto.farmacia.entity.Empleado;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    @Query("SELECT e FROM Empleado e WHERE e.activo = TRUE")
    public List<Empleado>findByActivo();
    
     @Query("SELECT e FROM Empleado e WHERE e.activo=TRUE AND e.dni=:dni")
    public Optional<Empleado> findByDniAndActivo(String dni);
}
