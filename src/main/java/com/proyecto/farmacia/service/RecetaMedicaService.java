package com.proyecto.farmacia.service;

import com.proyecto.farmacia.entity.RecetaMedica;
import com.proyecto.farmacia.interfaz.RecetaMedicaInterfaz;
import com.proyecto.farmacia.repository.RecetaMedicaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecetaMedicaService implements RecetaMedicaInterfaz {

    @Autowired
    private RecetaMedicaRepository repo;

    @Override
    public RecetaMedica guardar(RecetaMedica recetaMedica) {
        return repo.save(recetaMedica);
    }

    @Override
    public Optional<RecetaMedica> obtener(Integer id) {
        return repo.findById(id);
    }

    @Override
    public void eliminar(Integer id) {
        Optional<RecetaMedica> recetaOptional = repo.findById(id);
        if (recetaOptional.isPresent()) {
            RecetaMedica recetaMedica = recetaOptional.get();
            recetaMedica.setActivo(Boolean.FALSE);
            repo.save(recetaMedica);
        }

    }

    @Override
    public List<RecetaMedica> listar() {
        return repo.findByActivo();
    }

    public List<RecetaMedica> obtenerById(List<Integer> id) {
        return repo.findAllById(id);
    }
}
