package com.sysc.bulag_faust.core.exception;

import com.sysc.bulag_faust.core.dto.ErrorResponse;
import com.sysc.bulag_faust.core.exception.category.CategoryAlreadyExist;
import com.sysc.bulag_faust.core.exception.category.CategoryNotFound;
import com.sysc.bulag_faust.core.exception.tag.TagAlreadyExist;
import com.sysc.bulag_faust.core.exception.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgument(IllegalArgumentException e) {
        return ErrorResponse.builder().code("ILLEGAL_ARGUMENT").message(e.getMessage()).timestamp(LocalDateTime.now()).build();
   }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(NotFoundException e) {
        return ErrorResponse.builder().code("NOT_FOUND").message(e.getMessage()).timestamp(LocalDateTime.now()).build();
    }

    //user exceptions
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFound(UserNotFoundException e) {
        return ErrorResponse.builder().code("USER_NOT_FOUND").message(e.getMessage()).timestamp(LocalDateTime.now()).build();
    }

    //category
    //
    @ExceptionHandler(CategoryNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCategoryNotFound(CategoryNotFound e) {
        return ErrorResponse.builder().code("CATEGORY_NOT_FOUND").message(e.getMessage()).timestamp(LocalDateTime.now()).build();
    }

    @ExceptionHandler(CategoryAlreadyExist.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse  handleCategoryAlreadyExist(
            CategoryAlreadyExist e
    ) {
        return ErrorResponse.builder().code("CATEGORY_ALREADY_EXIST").message(e.getMessage()).timestamp(LocalDateTime.now()).build();
    }

    // Tag
    //
    @ExceptionHandler(TagAlreadyExist.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleTagAlreadyExist(TagAlreadyExist e) {
        return ErrorResponse.builder().code("TAG_ALREADY_EXIST").message(e.getMessage()).timestamp(LocalDateTime.now()).build();
    }
}
