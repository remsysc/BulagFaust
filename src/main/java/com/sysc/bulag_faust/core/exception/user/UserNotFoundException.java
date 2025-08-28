package com.sysc.bulag_faust.core.exception.user;

import com.sysc.bulag_faust.core.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(String id) {
        super("User: " + id + " not found");
    }
}
