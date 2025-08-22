package com.sysc.bulag_faust.post.exception;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(String id) {
        super("User: " + id + " not found");
    }
}
