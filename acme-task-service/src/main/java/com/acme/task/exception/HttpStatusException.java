package com.acme.task.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public abstract class HttpStatusException extends RuntimeException {

  private final HttpStatus statusCode;

  public HttpStatusException(String statusMessage, HttpStatus statusCode) {
    super(statusMessage);
    this.statusCode = statusCode;
  }

  public HttpStatusException(String statusMessage, HttpStatus statusCode, Exception e) {
    super(statusMessage, e);
    this.statusCode = statusCode;
  }

}
