package com.proyecto.farmacia.service;

import com.proyecto.farmacia.entity.Empleado;
import com.proyecto.farmacia.interfaz.EmpleadoInterfaz;
import com.proyecto.farmacia.repository.EmpleadoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoService implements EmpleadoInterfaz {

    @Autowired
    private EmpleadoRepository repo;

    @Override
    public Empleado guardar(Empleado empleado) {
        return repo.save(empleado);
    }

    @Override
    public void eliminar(Integer id) {
        Optional<Empleado> empleadoOptional = repo.findById(id);
        if (empleadoOptional.isPresent()) {
            Empleado empleado = empleadoOptional.get();
            empleado.setActivo(Boolean.FALSE);
            repo.save(empleado);
        }
    }

    @Override
    public Optional<Empleado> obtener(Integer id) {
        return repo.findById(id);
    }

    @Override
    public List<Empleado> listar() {
        return repo.findByActivo();
    }

    public Boolean existe(String dni) {
        return repo.findByDniAndActivo(dni).isPresent();
    }
}
