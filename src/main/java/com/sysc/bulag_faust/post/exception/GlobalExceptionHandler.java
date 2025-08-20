package com.sysc.bulag_faust.post.exception;

import com.sysc.bulag_faust.core.exception.NotFoundException;
import com.sysc.bulag_faust.core.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleNotFound(NotFoundException e) {
        return ApiResponse.error(e.getMessage());
    }
}
