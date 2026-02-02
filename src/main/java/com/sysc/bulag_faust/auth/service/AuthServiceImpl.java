package com.sysc.bulag_faust.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authManager;

  @Override
  public Authentication authenticate(String email, String password) {
    return authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

  }
}
