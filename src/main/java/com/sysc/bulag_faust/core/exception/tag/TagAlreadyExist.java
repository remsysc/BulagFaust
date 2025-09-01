package com.sysc.bulag_faust.core.exception.tag;

import com.sysc.bulag_faust.core.exception.AlreadyExistException;

public class TagAlreadyExist extends AlreadyExistException {

    public TagAlreadyExist(String message) {
        super("This " + message + "already exist.");
    }
}
