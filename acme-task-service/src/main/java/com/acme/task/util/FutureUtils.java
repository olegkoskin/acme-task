package com.acme.task.util;

import com.acme.task.exception.FutureException;
import com.acme.task.exception.HttpStatusException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

@UtilityClass
public class FutureUtils {

  /**
   * Waits if necessary for the computation to complete, and then retrieves its result.
   *
   * @param future   future
   * @param errorMsg error message
   * @param <T>      generic type
   * @return the computed result
   */
  public <T> T getSafety(Future<T> future, long timeout, String errorMsg) {
    try {
      return future.get(timeout, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw handleException(e, errorMsg);
    } catch (ExecutionException e) {
      throw handleException(e, errorMsg);
    } catch (TimeoutException e) {
      throw handleException(e, "Timeout!\n" + errorMsg);
    }
  }

  private RuntimeException handleException(Exception e, String errorMsg) {
    if (e.getCause() instanceof HttpStatusException) {
      HttpStatusException cause = (HttpStatusException) e.getCause();
      return new FutureException(errorMsg, cause.getStatusCode(), e);
    }

    return new FutureException(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR, e);
  }

}
