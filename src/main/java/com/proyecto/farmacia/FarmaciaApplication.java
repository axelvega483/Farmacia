package com.proyecto.farmacia;

import com.proyecto.farmacia.entity.Empleado;
import com.proyecto.farmacia.service.EmpleadoService;
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
    CommandLineRunner initDatabase(EmpleadoService empleadoService
    ) {
        return args -> {
            List<Empleado> empleados = empleadoService.listar();
            if (empleados.isEmpty()) {
                Empleado empleadoADMIN = new Empleado();
                empleadoADMIN.setRol(RolEmpleado.ADMIN);
                empleadoADMIN.setNombre("ADMIN");
                empleadoADMIN.setPassword("admin");
                empleadoADMIN.setEmail("admin@admin.com");
                empleadoADMIN.setDni("0");

                empleadoService.guardar(empleadoADMIN);
                System.out.println("Usuario administrador inicializado con éxito.");
            }
        };
    }

}
