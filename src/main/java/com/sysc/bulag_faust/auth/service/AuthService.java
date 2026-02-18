
package com.sysc.bulag_faust.auth.service;

import com.sysc.bulag_faust.auth.dto.AuthResponse;
import com.sysc.bulag_faust.auth.dto.SignUpRequest;

public interface AuthService {
  AuthResponse authenticate(String email, String password);

  AuthResponse register(SignUpRequest request);

}
