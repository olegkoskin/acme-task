package com.acme.task.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ExternalServiceException extends HttpStatusException {

  public ExternalServiceException(String statusMessage, HttpStatus statusCode) {
    super(statusMessage, statusCode);
  }

  public ExternalServiceException(String statusMessage, HttpStatus statusCode, Exception e) {
    super(statusMessage, statusCode, e);
  }

}
