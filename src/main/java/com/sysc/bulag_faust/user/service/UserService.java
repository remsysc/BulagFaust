package com.sysc.bulag_faust.user.service;

import java.util.UUID;

import com.sysc.bulag_faust.user.User;

public interface UserService {
  User getUserEntityById(UUID id);

  User getUserByUsername(String username);

  User getUserByEmail(String email);
}
