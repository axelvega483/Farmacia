package com.proyecto.farmacia.interfaz;

import com.proyecto.farmacia.entity.Medicamento;
import java.util.List;
import java.util.Optional;

public interface MedicamentoInterfaz {

    public Medicamento guardar(Medicamento medicamento);

    public void eliminar(Integer id);

    public Optional<Medicamento> obtener(Integer id);

    public List<Medicamento> listar();
}
