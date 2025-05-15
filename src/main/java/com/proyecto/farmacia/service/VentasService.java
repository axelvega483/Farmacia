package com.proyecto.farmacia.service;

import com.proyecto.farmacia.entity.Venta;
import com.proyecto.farmacia.interfaz.VentaInterfaz;
import com.proyecto.farmacia.repository.VentaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentasService implements VentaInterfaz {

    @Autowired
    private VentaRepository repo;

    @Override
    public Venta guardar(Venta venta) {
        return repo.save(venta);
    }

    @Override
    public Optional<Venta> obtener(Integer id) {
        return repo.findByIdActivo(id);
    }

    @Override
    public void eliminar(Integer id) {
        Optional<Venta> ventaOptional = repo.findById(id);
        if (ventaOptional.isPresent()) {
            Venta venta = ventaOptional.get();
            venta.setActivo(Boolean.FALSE);
            repo.save(venta);
        }
    }

    @Override
    public List<Venta> listar() {
        return repo.findALlActivo();
    }

    public List<Venta> obtenerById(List<Integer> id) {
        return repo.findAllById(id);
    }

}
