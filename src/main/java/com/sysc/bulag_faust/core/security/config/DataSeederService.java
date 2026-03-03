package com.sysc.bulag_faust.core.security.config;

import java.util.Set;

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

  @Transactional
  public void seed() {
    Role adminRole = findOrCreateRole("ROLE_ADMIN");
    findOrCreateRole("ROLE_USER");
    log.info("✓ Roles seeded");

    String adminEmail = "admin@localhost";
    if (!userRepository.existsByEmail(adminEmail)) {
      User admin = User.builder()
          .username("admin")
          .email(adminEmail)
          .password(passwordEncoder.encode("admin123"))
          .roles(Set.of(adminRole)) // already managed within this transaction ✅
          .build();
      userRepository.save(admin);
      log.info("✓ Default admin user created");
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
