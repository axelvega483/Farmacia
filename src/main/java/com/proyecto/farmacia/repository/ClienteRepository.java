package com.proyecto.farmacia.repository;

import com.proyecto.farmacia.entity.Cliente;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    boolean existsByDniAndActivoTrue(String dni);
}
