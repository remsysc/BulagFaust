package com.sysc.bulag_faust.core.exception.category;

import com.sysc.bulag_faust.core.exception.AlreadyExistException;

public class CategoryAlreadyExist extends AlreadyExistException {

    public CategoryAlreadyExist(String category) {
        super("This " + category + " already exist: ");
    }
}
