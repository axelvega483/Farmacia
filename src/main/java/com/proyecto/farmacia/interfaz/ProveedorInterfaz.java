package com.proyecto.farmacia.interfaz;

import com.proyecto.farmacia.entity.Proveedor;
import java.util.List;
import java.util.Optional;

public interface ProveedorInterfaz {

    public Proveedor guardar(Proveedor proveedor);

    public void eliminar(Integer id);

    public Optional<Proveedor> obtener(Integer id);

    public List<Proveedor> listar();
}
