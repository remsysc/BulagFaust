package com.sysc.bulag_faust.core.exception.tag;

import com.sysc.bulag_faust.core.exception.NotFoundException;

public class TagNotFound extends NotFoundException {

    public TagNotFound(String msg) {
        super("Tag not found: " + msg);
    }
}
