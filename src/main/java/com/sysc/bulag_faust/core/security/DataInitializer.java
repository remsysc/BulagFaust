package com.sysc.bulag_faust.core.security;

import java.util.Set;
import java.util.UUID;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sysc.bulag_faust.role.Role;
import com.sysc.bulag_faust.user.User;
import com.sysc.bulag_faust.user.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @EventListener(ApplicationReadyEvent.class)
  public void createTestUser() {
    if (userRepository.findByEmail("test@example.com").isEmpty()) {
      User testUser = User.builder()
          .username("testuser") // ← ADD THIS (NOT NULL required!)
          .email("test@example.com")
          .password(passwordEncoder.encode("password123"))
          .build();
      userRepository.save(testUser);
    }
  }

  private Role createUserRole() {
    Role role = new Role();
    role.setId(UUID.randomUUID());
    role.setName("USER"); // Or fetch from Role repo
    return role;
  }

}
