package com.sysc.bulag_faust.user.service;

import com.sysc.bulag_faust.user.entities.User;
import java.util.UUID;

public interface IUserService {
    User getUserEntityById(UUID id);
}
