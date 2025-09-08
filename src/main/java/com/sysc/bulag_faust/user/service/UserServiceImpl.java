package com.sysc.bulag_faust.user.service;

import com.sysc.bulag_faust.core.exception.user.UserNotFoundException;
import com.sysc.bulag_faust.user.entities.User;
import com.sysc.bulag_faust.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserEntityById(UUID id) {
        return userRepository
            .findById(id)
            .orElseThrow(() ->
                new UserNotFoundException("User: " + id + " not found")
            );
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository
            .findByUsername(username)
            .orElseThrow(() ->
                new UserNotFoundException("User: " + username + " not found")
            );
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository
            .findByEmail(email)
            .orElseThrow(() ->
                new UserNotFoundException("User: " + email + " not found")
            );
    }

    // @Override
    // public User getCurrentUser() {
    //     // Implementation depends on your authentication mechanism
    //     Authentication authentication =
    //         SecurityContextHolder.getContext().getAuthentication();
    //     if (authentication == null || !authentication.isAuthenticated()) {
    //         throw new UserNotFoundException("No authenticated user found");
    //     }

    //     String username = authentication.getName();
    //     return getUserByUsername(username);
    // }
}
