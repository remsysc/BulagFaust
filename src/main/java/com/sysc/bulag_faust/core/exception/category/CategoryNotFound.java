package com.sysc.bulag_faust.core.exception.category;

import com.sysc.bulag_faust.core.exception.NotFoundException;

public class CategoryNotFound extends NotFoundException {

    public CategoryNotFound(String msg) {
        super("Category not found: " + msg);
    }
}
