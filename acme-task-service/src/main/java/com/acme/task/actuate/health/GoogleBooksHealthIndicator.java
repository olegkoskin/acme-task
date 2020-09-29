package com.acme.task.actuate.health;

import static com.acme.task.client.GoogleBooksClient.GoogleBooksClientFallback.FALLBACK_VALUE;

import com.acme.task.client.GoogleBooksClient;
import com.acme.task.model.google.BooksVolumes;
import com.acme.task.model.google.PrintType;
import com.acme.task.model.google.Projection;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GoogleBooksHealthIndicator extends AbstractHealthIndicator {

  final GoogleBooksClient googleBooksClient;

  @Override
  protected void doHealthCheck(Builder builder) throws Exception {
    builder.withDetail("External service", "Google Books");
    BooksVolumes bookVolumes = googleBooksClient
        .getBookVolumes("test", 1, PrintType.BOOKS, Projection.LITE);
    if (FALLBACK_VALUE.equals(bookVolumes)) {
      builder.down().withDetail("Error", "Fallback value was returned");
    } else {
      builder.up().withDetail("Test request response", bookVolumes);
    }
  }

}
