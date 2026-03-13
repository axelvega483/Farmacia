package com.proyecto.farmacia;

import com.proyecto.farmacia.DTOs.Usuarios.UsuarioGetDTO;
import com.proyecto.farmacia.entity.Usuario;
import com.proyecto.farmacia.service.UsuarioService;
import com.proyecto.farmacia.util.RolEmpleado;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FarmaciaApplication {

    public static void main(String[] args) {
        SpringApplication.run(FarmaciaApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(UsuarioService empleadoService
    ) {
        return args -> {
            List<UsuarioGetDTO> empleados = empleadoService.findAll();
            if (empleados.isEmpty()) {
                Usuario empleadoADMIN = new Usuario();
                empleadoADMIN.setRol(RolEmpleado.ADMIN);
                empleadoADMIN.setNombre("ADMIN");
                empleadoADMIN.setPassword("admin");
                empleadoADMIN.setEmail("admin@admin.com");
                empleadoADMIN.setDni("12345678");
                empleadoADMIN.setActivo(true);
                empleadoService.save(empleadoADMIN);
                System.out.println("Usuario administrador inicializado con éxito.");
            }
        };
    }

}
