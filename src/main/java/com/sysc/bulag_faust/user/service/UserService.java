package com.sysc.bulag_faust.user.service;

import com.sysc.bulag_faust.user.entities.User;
import java.util.UUID;

public interface UserService {
    User getUserEntityById(UUID id);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    //User getCurrentUser();
}
