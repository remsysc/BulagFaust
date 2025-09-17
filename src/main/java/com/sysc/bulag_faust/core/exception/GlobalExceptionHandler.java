package com.sysc.bulag_faust.core.exception;

import com.sysc.bulag_faust.core.exception.category.CategoryAlreadyExist;
import com.sysc.bulag_faust.core.exception.category.CategoryNotFound;
import com.sysc.bulag_faust.core.exception.tag.TagAlreadyExist;
import com.sysc.bulag_faust.core.exception.user.UserNotFoundException;
import com.sysc.bulag_faust.core.response.ApiErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    //add spring exceptions
    // i.e. wrong paths etc

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public  ApiErrorResponse handleIllegalState(IllegalStateException ex)
    {
        log.warn("Illegal State: {}", ex.getMessage());
        return buildErrorResponse("ILLEGAL_STATE",ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleIllegalArgument(IllegalArgumentException e) {
        log.warn("Validation failed: {}", e.getMessage());
        return buildErrorResponse("ILLEGAL_ARGUMENT",e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleEntityNotFound(EntityNotFoundException ex) {
        log.warn("Entity not found: {}", ex.getMessage());
        return  buildErrorResponse("ENTITY_NOT_FOUND",ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrorResponse handleAcessDenied(AccessDeniedException ex) {
        //log.warn("Access denied for user: {}");
        return buildErrorResponse("ACCESS_DENIED",ex.getMessage());
    }
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleConstraintViolation(ConstraintViolationException ex) {
        // ERROR because:
        // - Database constraint violated unexpectedly
        // - Indicates data integrity issue or bug
        // - Validation should have caught this earlier
        log.error("Unexpected database constraint violation: ", ex);
        return buildErrorResponse("CONSTRAINT_VIOLATION", "Data integrity error");
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleNullPointer(NullPointerException ex) {
        // ERROR because:
        // - This is a bug in the code
        // - Should never happen in production
        // - Requires immediate developer attention
        log.error("Null pointer exception - possible bug: ", ex);
        return buildErrorResponse("NULL_POINTER", "Internal system error");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleUnexpected(Exception ex, HttpServletRequest request) {
        // ERROR because:
        // - Truly unexpected system failure
        // - Unknown problem that needs investigation
        // - Potential system instability
        log.error("Unexpected error at {}: ", request.getRequestURI(), ex);
        return buildErrorResponse("INTERNAL_SERVER_ERROR", "An unexpected error occurred");
    }


    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleNotFound(NotFoundException e) {
        return ApiErrorResponse.builder().code("NOT_FOUND").message(e.getMessage()).timestamp(LocalDateTime.now()).build();
    }

    //user exceptions
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleUserNotFound(UserNotFoundException e) {
        return ApiErrorResponse.builder().code("USER_NOT_FOUND").message(e.getMessage()).timestamp(LocalDateTime.now()).build();
    }

    //category
    //
    @ExceptionHandler(CategoryNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleCategoryNotFound(CategoryNotFound e) {
        return ApiErrorResponse.builder().code("CATEGORY_NOT_FOUND").message(e.getMessage()).timestamp(LocalDateTime.now()).build();
    }

    @ExceptionHandler(CategoryAlreadyExist.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleCategoryAlreadyExist(CategoryAlreadyExist e) {
        return ApiErrorResponse.builder().code("CATEGORY_ALREADY_EXIST").message(e.getMessage()).timestamp(LocalDateTime.now()).build();
    }

    // Tag
    //
    @ExceptionHandler(TagAlreadyExist.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorResponse handleTagAlreadyExist(TagAlreadyExist e) {
        return ApiErrorResponse.builder().code("TAG_ALREADY_EXIST").message(e.getMessage()).timestamp(LocalDateTime.now()).build();
    }


    private ApiErrorResponse buildErrorResponse(String code, String message){
        return ApiErrorResponse.builder().code(code).message(message).timestamp(LocalDateTime.now()).build();
    }
}
