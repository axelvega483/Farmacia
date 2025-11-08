package com.proyecto.farmacia.repository;

import com.proyecto.farmacia.entity.Cliente;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Override
    Optional<Cliente> findById(Integer id);

    @Query("SELECT c FROM Cliente c WHERE c.activo=TRUE")
    List<Cliente> findByActivo();

    @Query("SELECT c FROM Cliente c WHERE c.activo=TRUE AND c.dni=:dni")
    Optional<Cliente> findByDniAndActivo(String dni);
}
