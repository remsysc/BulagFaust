
package com.sysc.bulag_faust.auth.service;

import org.springframework.security.core.Authentication;

public interface AuthService {
  Authentication authenticate(String email, String password);

}
