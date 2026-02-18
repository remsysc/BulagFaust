package com.sysc.bulag_faust.user.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sysc.bulag_faust.core.exceptions.base_exception.NotFoundException;
import com.sysc.bulag_faust.user.User;
import com.sysc.bulag_faust.user.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public User getUserEntityById(UUID id) {
    return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User", id));
  }

  @Override
  public User getUserByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new NotFoundException("User", username));
  }

  @Override
  public User getUserByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User", email));

  }

}
