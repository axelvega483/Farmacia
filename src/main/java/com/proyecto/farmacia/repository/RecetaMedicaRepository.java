package com.proyecto.farmacia.repository;

import com.proyecto.farmacia.entity.RecetaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecetaMedicaRepository extends JpaRepository<RecetaMedica, Integer> {

}
