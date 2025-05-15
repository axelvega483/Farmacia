package com.proyecto.farmacia.interfaz;

import com.proyecto.farmacia.entity.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteInterfaz {

    public Cliente guardar(Cliente cliente);

    public void eliminar(Integer id);

    public Optional<Cliente> obtener(Integer id);

    public List<Cliente> listar();

}
