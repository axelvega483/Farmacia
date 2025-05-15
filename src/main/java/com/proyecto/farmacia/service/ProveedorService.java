package com.proyecto.farmacia.service;

import com.proyecto.farmacia.entity.Proveedor;
import com.proyecto.farmacia.interfaz.ProveedorInterfaz;
import com.proyecto.farmacia.repository.ProveedorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProveedorService implements ProveedorInterfaz {

    @Autowired
    private ProveedorRepository repo;

    @Override
    public Proveedor guardar(Proveedor proveedor) {
        return repo.save(proveedor);
    }

    @Override
    public void eliminar(Integer id) {
        Optional<Proveedor> proveedorOptional = repo.findById(id);
        if (proveedorOptional.isPresent()) {
            Proveedor proveedor = proveedorOptional.get();
            proveedor.setActivo(Boolean.FALSE);
            repo.save(proveedor);
        }
    }

    @Override
    public Optional<Proveedor> obtener(Integer id) {
        return repo.findById(id);
    }

    @Override
    public List<Proveedor> listar() {
        return repo.findByActivo();
    }


    
    public Optional<Proveedor>obtenerPorNombre(String nombre){
        return repo.findByNombre(nombre);
    }
    
}
