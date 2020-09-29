package com.acme.task.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class FutureException extends HttpStatusException {

  public FutureException(String statusMessage) {
    super(statusMessage, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  public FutureException(String statusMessage, HttpStatus statusCode) {
    super(statusMessage, statusCode);
  }

  public FutureException(String statusMessage, HttpStatus statusCode, Exception e) {
    super(statusMessage, statusCode, e);
  }

}
