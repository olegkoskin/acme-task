package com.acme.task.exception;

import static feign.FeignException.errorStatus;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FeignErrorDecoder implements ErrorDecoder {

  @Override
  public Exception decode(String methodKey, Response response) {
    FeignException feignException = errorStatus(methodKey, response);
    return new ExternalServiceException("Exception while calling external service",
        HttpStatus.INTERNAL_SERVER_ERROR, feignException);
  }


}
