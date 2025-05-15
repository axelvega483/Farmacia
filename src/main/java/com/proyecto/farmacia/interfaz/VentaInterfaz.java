package com.proyecto.farmacia.interfaz;

import com.proyecto.farmacia.entity.Venta;
import java.util.List;
import java.util.Optional;

public interface VentaInterfaz {

    public Venta guardar(Venta venta);

    public Optional<Venta> obtener(Integer id);

    public void eliminar(Integer id);

    public List<Venta> listar();
}
