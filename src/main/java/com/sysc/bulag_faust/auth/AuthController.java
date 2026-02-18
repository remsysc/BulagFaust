package com.sysc.bulag_faust.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sysc.bulag_faust.auth.dto.AuthResponse;
import com.sysc.bulag_faust.auth.dto.SignUpRequest;
import com.sysc.bulag_faust.auth.service.AuthService;
import com.sysc.bulag_faust.core.request.LoginRequest;
import com.sysc.bulag_faust.core.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/auth")

public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
    AuthResponse authResponse = authService.authenticate(request.email(), request.password());
    return ResponseEntity.ok().body(ApiResponse.success("Login successfull", authResponse));
  }

  @PostMapping("/register")
  public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody SignUpRequest request) {

    AuthResponse authResponse = authService.register(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Registered successfull", authResponse));
  }
}
