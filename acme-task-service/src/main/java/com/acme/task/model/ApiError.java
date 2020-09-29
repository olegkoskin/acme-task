package com.acme.task.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ApiError {

  HttpStatus status;
  String message;
  List<String> errors;

}
