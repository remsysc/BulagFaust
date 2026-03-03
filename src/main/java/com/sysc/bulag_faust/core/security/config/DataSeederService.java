package com.sysc.bulag_faust.core.security.config;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysc.bulag_faust.role.Role;
import com.sysc.bulag_faust.role.RoleRepository;
import com.sysc.bulag_faust.user.User;
import com.sysc.bulag_faust.user.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataSeederService {

  private final RoleRepository roleRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  @Value("${admin.email}")
  private String adminEmail;
  @Value("${admin.password}")
  private String password;

  @Transactional
  public void seed() {
    // Seed roles
    findOrCreateRole("ROLE_ADMIN");
    findOrCreateRole("ROLE_USER");
    log.info("✓ Roles seeded");

    // Create admin user - re-fetch to ensure managed entity
    if (!userRepository.existsByEmail(adminEmail)) {
      Role adminRoleManaged = roleRepository.findByName("ROLE_ADMIN")
          .orElseThrow(() -> new IllegalStateException("ROLE_ADMIN not found"));

      User admin = User.builder()
          .username("admin")
          .email(adminEmail)
          .password(passwordEncoder.encode(password))
          .roles(Set.of(adminRoleManaged))
          .build();
      userRepository.save(admin);
      log.info("✓ Default admin user created: {}");
    }
  }

  private Role findOrCreateRole(String roleName) {
    return roleRepository.findByName(roleName)
        .orElseGet(() -> {
          Role role = new Role();
          role.setName(roleName);
          return roleRepository.save(role);
        });
  }
}
