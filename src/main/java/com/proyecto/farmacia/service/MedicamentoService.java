package com.proyecto.farmacia.service;

import com.proyecto.farmacia.entity.Medicamento;
import com.proyecto.farmacia.interfaz.MedicamentoInterfaz;
import com.proyecto.farmacia.repository.MedicamentoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicamentoService implements MedicamentoInterfaz {

    @Autowired
    private MedicamentoRepository repo;

    @Override
    public Medicamento guardar(Medicamento medicamento) {
        return repo.save(medicamento);
    }

    @Override
    public void eliminar(Integer id) {
        Optional<Medicamento> medicamento = repo.findById(id);
        if (medicamento.isPresent()) {
            Medicamento m = medicamento.get();
            m.setActivo(Boolean.FALSE);
            repo.save(m);
        }
    }

    @Override
    public Optional<Medicamento> obtener(Integer id) {
        return repo.findById(id);
    }

    @Override
    public List<Medicamento> listar() {
        return repo.findByActivo();
    }
    
    public boolean medicamentoExiste(String nombre, Integer proveedorId){
        return repo.findByNombreAndProveedor(nombre, proveedorId).isPresent();
    }
    
    public List<Medicamento>buscarPorNombre(String nombre){
        return repo.findByNombre(nombre);
    }
    
    public List<Medicamento>obtenerById(List<Integer> id){
        return repo.findAllById(id);
    }
}
