package com.proyecto.farmacia.interfaz;

import com.proyecto.farmacia.entity.RecetaMedica;
import java.util.List;
import java.util.Optional;

public interface RecetaMedicaInterfaz {

    public RecetaMedica guardar(RecetaMedica recetaMedica);
    
    public Optional<RecetaMedica>obtener(Integer id);
    
    public void eliminar(Integer id);
    
    public List<RecetaMedica>listar();
}
