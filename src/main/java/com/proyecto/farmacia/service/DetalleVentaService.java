package com.proyecto.farmacia.service;

import com.proyecto.farmacia.entity.DetalleVenta;
import com.proyecto.farmacia.repository.DetalleVentaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleVentaService {

    @Autowired
    private DetalleVentaRepository repo;

    public List<DetalleVenta> obtenerById(List<Integer> id) {
        return repo.findAllById(id);
    }
}
