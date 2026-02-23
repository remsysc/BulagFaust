package com.sysc.bulag_faust.core.exceptions;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sysc.bulag_faust.core.exceptions.base_exception.NotFoundException;
import com.sysc.bulag_faust.core.exceptions.base_exception.AlreadyExistException;
import com.sysc.bulag_faust.core.response.ApiErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  // add spring exceptions
  // i.e. wrong paths etc
  //
  // TODO: add NoResourceFoundException

  @ExceptionHandler(UsernameNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ApiErrorResponse handleUsernameNotFound(UsernameNotFoundException ex) {
    log.warn("Username not found: {}", ex.getMessage());
    return buildErrorResponse("NOT_FOUND", ex.getMessage());
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ApiErrorResponse handleDataIntegrityViolation(DataIntegrityViolationException e) {
    log.error("Unexpected database integrity violation: {}", e);
    return buildErrorResponse("DATA_INTEGRITY_VIOLATION", e.getMessage());
  }

  @ExceptionHandler(BadCredentialsException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ApiErrorResponse handleBadCredentials(BadCredentialsException e) {
    log.warn("Bad Credentials: {}", e.getMessage());
    return buildErrorResponse("UNAUTHORIZED", e.getMessage());
  }

  @ExceptionHandler(IllegalStateException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ApiErrorResponse handleIllegalState(IllegalStateException ex) {
    log.warn("Illegal State: {}", ex.getMessage());
    return buildErrorResponse("ILLEGAL_STATE", ex.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiErrorResponse handleIllegalArgument(IllegalArgumentException e) {
    log.warn("Validation failed: {}", e.getMessage());
    return buildErrorResponse("ILLEGAL_ARGUMENT", e.getMessage());
  }

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ApiErrorResponse handleAcessDenied(AccessDeniedException ex) {
    // log.warn("Access denied for user: {}");
    return buildErrorResponse("ACCESS_DENIED", ex.getMessage());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
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
    log.warn("Entity not found: {}", e.getMessage());
    return ApiErrorResponse.builder().code("NOT_FOUND").message(e.getMessage()).timestamp(LocalDateTime.now()).build();
  }

  @ExceptionHandler(AlreadyExistException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ApiErrorResponse handleAlreadyExist(AlreadyExistException e) {

    log.warn("Entity already exists: {}", e.getMessage());
    return ApiErrorResponse.builder().code("CONFLICT").message(e.getMessage()).timestamp(LocalDateTime.now()).build();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiErrorResponse handleValidation(MethodArgumentNotValidException e) {
    String message = e.getBindingResult().getFieldErrors().stream()
        .map(err -> err.getDefaultMessage())
        .collect(Collectors.joining(", "));

    return buildErrorResponse(
        "VALIDATION_ERROR", message);
  }

  private ApiErrorResponse buildErrorResponse(String code, String message) {
    return ApiErrorResponse.builder().code(code).message(message).timestamp(LocalDateTime.now()).build();
  }

}
