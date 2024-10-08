package com.sh.financial.auth.web.advice;

import com.sh.financial.auth.exception.AuthAPIException;
import com.sh.financial.auth.exception.ResourceNotFoundException;
import com.sh.financial.utility.web.model.res.ErrorDetails;
import jakarta.servlet.ServletException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.sh.financial.auth.exception.ApplicationErrorCode;
import com.sh.financial.utility.web.advice.ExceptionHandlerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Date;

@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler extends ExceptionHandlerAdvice {
  public ApplicationExceptionHandler(@Qualifier("globalMessageSource") MessageSource messageSource) {
    super(messageSource);
  }

  @ExceptionHandler(Throwable.class)
  public ResponseEntity<?> handle(HttpServletRequest request, Throwable e) {
    log.error("{}", ExceptionUtils.getStackTrace(e));

    return error(ApplicationErrorCode.INTERNAL_ERROR_SERVER);
  }
  
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<?> handle(HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
    return error(ApplicationErrorCode.INVALID_HTTP_REQUEST_METHOD);
  }
  
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handle(HttpServletRequest request, MethodArgumentNotValidException e) {
    return error(ApplicationErrorCode.INVALID_REQUEST_PARAMETER, extractFieldErrors(e.getBindingResult()));
  }
  
  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<?> handle(HttpServletRequest request, NoResourceFoundException e) {
    return error(ApplicationErrorCode.INVALID_HTTP_REQUEST_RESOURCE, e.getResourcePath());
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest webRequest) {
    ErrorDetails errorDetails = new ErrorDetails(
            new Date(), ex.getMessage(), webRequest.getDescription(false)
    );
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(AuthAPIException.class)
  public ResponseEntity<ErrorDetails> handleAuthAPIException(AuthAPIException ex, WebRequest webRequest) {
    ErrorDetails errorDetails = new ErrorDetails(
            new Date(), ex.getMessage(), webRequest.getDescription(false)
    );
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AuthorizationDeniedException.class)
  public ResponseEntity<ErrorDetails> handleAuthorizationDeniedException(AuthorizationDeniedException ex, WebRequest webRequest) {
    ErrorDetails errorDetails = new ErrorDetails(
            new Date(), ex.getMessage(), webRequest.getDescription(false)
    );
    return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ErrorDetails> handleUsernameNotFoundException(HttpServletRequest request, UsernameNotFoundException e) {
    ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(), request.getRequestURI());
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ServletException.class)
  public ResponseEntity<ErrorDetails> handleServletException(HttpServletRequest request, ServletException e) {
      ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(), request.getRequestURI());
      return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorDetails> handleExceptionIO(HttpServletRequest request, IOException e) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
