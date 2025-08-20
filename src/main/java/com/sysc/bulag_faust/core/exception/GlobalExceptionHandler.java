package com.sysc.bulag_faust.core.exception;

import com.sysc.bulag_faust.core.response.ApiResponse;
import com.sysc.bulag_faust.post.exception.UserNotFoundException;
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
}
