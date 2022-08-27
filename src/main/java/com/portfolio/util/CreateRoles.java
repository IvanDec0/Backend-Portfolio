package com.portfolio.util;

import com.portfolio.security.entity.Rol;
import com.portfolio.security.enums.RolNombre;
import com.portfolio.security.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * Crear roles
 * Una vez creados, comentar
 */

@Component
public class CreateRoles implements CommandLineRunner {

    
    @Autowired
    RolService rolService;

    @Override
    public void run(String... args) throws Exception {

        Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
        rolService.save(rolAdmin);

    }
}
