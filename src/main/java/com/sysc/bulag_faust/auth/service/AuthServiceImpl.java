package com.sysc.bulag_faust.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysc.bulag_faust.auth.dto.AuthResponse;
import com.sysc.bulag_faust.auth.dto.SignUpRequest;
import com.sysc.bulag_faust.core.exceptions.base_exception.AlreadyExistException;
import com.sysc.bulag_faust.core.security.jwt.JwtUtils;
import com.sysc.bulag_faust.core.security.user.SecurityUserDetails;
import com.sysc.bulag_faust.user.User;
import com.sysc.bulag_faust.user.UserRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authManager;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtils jwtUtils;

  @Value("${auth.token.expirationInMils}")
  private long expirationTime;

  @Override
  public AuthResponse authenticate(@NonNull String email, @NonNull String password) {

    Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

    String token = jwtUtils.generateTokenForUser(auth);

    SecurityUserDetails userDetails = (SecurityUserDetails) auth.getPrincipal();
    return new AuthResponse(userDetails.getId(), "Bearer", token, expirationTime / 1000);
  }

  @Transactional
  @Override
  public AuthResponse register(@NonNull SignUpRequest request) {

    if (userRepository.existsByEmail(request.email())) {
      throw new AlreadyExistException("User", request.email());
    }
    User user = User.builder().username(request.username()).email(request.email())
        .password(passwordEncoder.encode(request.password()))
        .build();
    User saved = userRepository.save(user);
    SecurityUserDetails userDetails = SecurityUserDetails.buildUserDetails(saved);
    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
        userDetails.getAuthorities());
    String token = jwtUtils.generateTokenForUser(auth);

    return new AuthResponse(saved.getId(), "Bearer", token, expirationTime / 1000);
  }
}
