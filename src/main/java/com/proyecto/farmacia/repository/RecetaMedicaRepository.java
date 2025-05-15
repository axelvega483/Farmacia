package com.proyecto.farmacia.repository;

import com.proyecto.farmacia.entity.RecetaMedica;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecetaMedicaRepository extends JpaRepository<RecetaMedica, Integer> {

    @Query("SELECT r FROM RecetaMedica r WHERE r.activo=TRUE")
    public List<RecetaMedica>findByActivo();
    
    
}
