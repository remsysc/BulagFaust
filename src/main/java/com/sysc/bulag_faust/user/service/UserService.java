package com.sysc.bulag_faust.user.service;

import com.sysc.bulag_faust.post.exception.UserNotFoundException;
import com.sysc.bulag_faust.user.entities.User;
import com.sysc.bulag_faust.user.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {

    private UserRepository userRepository;

    @Override
    public User getUserEntityById(UUID id) {
        return userRepository
            .findById(id)
            .orElseThrow(() ->
                new UserNotFoundException("User: " + id + " not found")
            );
    }
}
