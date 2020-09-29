package com.acme.task.exception;

import com.acme.task.model.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

  /**
   * Handles exceptions and returns appropriate response.
   *
   * @param e       thrown exception
   * @param request web request
   * @return response entity with error status code and message
   */
  @ExceptionHandler({ HttpStatusException.class })
  public ResponseEntity<Object> handleDataRetrievingException(HttpStatusException e,
      WebRequest request) {
    log.error("HttpStatusException happened", e);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    ApiError apiError = ApiError.builder()
        .status(e.getStatusCode())
        .message(e.getMessage())
        .build();
    return handleExceptionInternal(e, apiError, headers, e.getStatusCode(), request);
  }

  /**
   * Handles exceptions and returns appropriate response.
   *
   * @param e       thrown exception
   * @param request web request
   * @return response entity with error status code and message
   */
  @ExceptionHandler({ Exception.class })
  public ResponseEntity<Object> handleDataRetrievingException(Exception e, WebRequest request) {
    log.error("Exception happened", e);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    ApiError apiError = ApiError.builder()
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .message("System Unavailable")
        .build();
    return handleExceptionInternal(e, apiError, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

}
