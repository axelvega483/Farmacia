package com.proyecto.farmacia.service;

import com.proyecto.farmacia.entity.Empleado;
import com.proyecto.farmacia.interfaz.EmpleadoInterfaz;
import com.proyecto.farmacia.repository.EmpleadoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoService implements EmpleadoInterfaz, UserDetailsService {

    @Autowired
    private EmpleadoRepository repo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Empleado guardar(Empleado empleado) {
        empleado.setPassword(passwordEncoder.encode(empleado.getPassword()));
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

    public Optional<Empleado> findByCorreo(String email) {
        return repo.findByCorreo(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Empleado empleado = repo.findByCorreo(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        return new org.springframework.security.core.userdetails.User(
                empleado.getEmail(),
                empleado.getPassword(),
                empleado.getActivo(), // enabled
                true, // accountNonExpired
                true, // credentialsNonExpired
                true, // accountNonLocked
                List.of(new SimpleGrantedAuthority("ROLE_" + empleado.getRol().name()))
        );
    }
}
