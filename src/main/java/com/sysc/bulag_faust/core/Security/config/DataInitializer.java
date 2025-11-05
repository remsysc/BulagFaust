package com.sysc.bulag_faust.core.Security.config;

import com.sysc.bulag_faust.role.Role;
import com.sysc.bulag_faust.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByName("ROLE_USER").isEmpty())
            roleRepository.save(new Role("ROLE_USER"));
        if (roleRepository.findByName("ROLE_ADMIN").isEmpty())
            roleRepository.save(new Role("ROLE_ADMIN"));
        if (roleRepository.findByName("ROLE_VIEWER").isEmpty())
            roleRepository.save(new Role("ROLE_VIEWER"));
    }
}
