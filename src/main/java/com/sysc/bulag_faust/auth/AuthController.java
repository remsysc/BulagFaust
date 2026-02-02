package com.sysc.bulag_faust.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sysc.bulag_faust.auth.service.AuthService;
import com.sysc.bulag_faust.core.request.LoginRequest;
import com.sysc.bulag_faust.core.response.ApiResponse;
import com.sysc.bulag_faust.core.response.AuthResponse;
import com.sysc.bulag_faust.core.security.jwt.JwtUtils;
import com.sysc.bulag_faust.core.security.user.SecurityUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/auth")

public class AuthController {

  private final AuthService authService;
  private final JwtUtils jwtUtils;

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
    Authentication auth = authService.authenticate(request.email(), request.password());

    String token = jwtUtils.generateTokenForUser(auth);

    SecurityUserDetails userDetails = (SecurityUserDetails) auth.getPrincipal();
    AuthResponse authResponse = new AuthResponse(userDetails.getId(), "Bearer", token, 84000L);

    return ResponseEntity.ok().body(ApiResponse.success("Login successfull", authResponse));
  }

}
