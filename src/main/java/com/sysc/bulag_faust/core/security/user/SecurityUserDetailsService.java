package com.sysc.bulag_faust.core.security.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sysc.bulag_faust.user.User;
import com.sysc.bulag_faust.user.UserRepository;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(@NotNull String email) throws UsernameNotFoundException {
    log.debug("Auth attempt for email: {}", email);
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> {
          log.warn("User not found with email: {}", email);
          return new UsernameNotFoundException("User not found with email:" + email);
        });
    log.debug("Successfully loaded user: {}", user.getEmail());
    return SecurityUserDetails.buildUserDetails(user);
  }
}
