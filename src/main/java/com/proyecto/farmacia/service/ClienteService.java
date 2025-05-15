package com.proyecto.farmacia.service;

import com.proyecto.farmacia.entity.Cliente;
import com.proyecto.farmacia.interfaz.ClienteInterfaz;
import com.proyecto.farmacia.repository.ClienteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService implements ClienteInterfaz {

    @Autowired
    private ClienteRepository repo;

    @Override
    public Cliente guardar(Cliente cliente) {
        return repo.save(cliente);
    }

    @Override
    public void eliminar(Integer id) {
        Optional<Cliente> optionalCliente = repo.findById(id);
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            cliente.setActivo(Boolean.FALSE);
            repo.save(cliente);
        }
    }

    @Override
    public Optional<Cliente> obtener(Integer id) {
        return repo.findById(id);
    }

    @Override
    public List<Cliente> listar() {
        return repo.findByActivo();
    }
    
    public boolean obtenerDniActivo(String dni){
        return repo.findByDniAndActivo(dni).isPresent();
    }

}
