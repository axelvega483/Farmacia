package com.proyecto.farmacia.interfaz;

import com.proyecto.farmacia.entity.Empleado;
import java.util.List;
import java.util.Optional;

public interface EmpleadoInterfaz {

    public Empleado guardar(Empleado empleado);

    public void eliminar(Integer id);

    public Optional<Empleado> obtener(Integer id);
    
    public List<Empleado>listar();

}
