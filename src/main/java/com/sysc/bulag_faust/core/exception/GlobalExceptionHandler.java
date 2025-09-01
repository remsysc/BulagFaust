package com.sysc.bulag_faust.core.exception;

import com.sysc.bulag_faust.core.exception.post.CategoryAlreadyExist;
import com.sysc.bulag_faust.core.exception.post.CategoryNotFound;
import com.sysc.bulag_faust.core.exception.tag.TagAlreadyExist;
import com.sysc.bulag_faust.core.exception.user.UserNotFoundException;
import com.sysc.bulag_faust.core.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleNotFound(NotFoundException e) {
        return ApiResponse.error(e.getMessage());
    }

    //user exceptions
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleUserNotFound(UserNotFoundException e) {
        return ApiResponse.error(e.getMessage());
    }

    //category
    //
    @ExceptionHandler(CategoryNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleCategoryNotFound(CategoryNotFound e) {
        return ApiResponse.error(e.getMessage());
    }

    @ExceptionHandler(CategoryAlreadyExist.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Void> handleCategoryAlreadyExist(
        CategoryAlreadyExist e
    ) {
        return ApiResponse.error(e.getMessage());
    }

    // Tag
    //
    @ExceptionHandler(TagAlreadyExist.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<Void> handleTagAlreadyExist(TagAlreadyExist e) {
        return ApiResponse.error(e.getMessage());
    }
}
