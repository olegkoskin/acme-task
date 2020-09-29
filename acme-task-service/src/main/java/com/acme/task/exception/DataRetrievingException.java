package com.acme.task.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class DataRetrievingException extends HttpStatusException {

  public DataRetrievingException(String statusMessage, Exception e) {
    super(statusMessage, HttpStatus.INTERNAL_SERVER_ERROR, e);
  }

  public DataRetrievingException(String statusMessage, HttpStatus statusCode) {
    super(statusMessage, statusCode);
  }

  public DataRetrievingException(String statusMessage, HttpStatus statusCode, Exception e) {
    super(statusMessage, statusCode, e);
  }

}
