package com.proyecto.farmacia.repository;

import com.proyecto.farmacia.entity.Empleado;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {

    @Query("SELECT e FROM Empleado e WHERE e.activo=TRUE AND e.dni=:dni")
    Optional<Empleado> findByDniAndActivo(String dni);

    @Query("SELECT e FROM Empleado e WHERE e.email=:email AND e.password=:password AND e.activo=true")
    Optional<Empleado> findByCorreoAndPassword(@Param("email") String email, @Param("password") String password);
}
