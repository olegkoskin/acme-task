package com.acme.task.actuate.health;

import static com.acme.task.client.ItunesClient.ItunesClientFallback.FALLBACK_VALUE;

import com.acme.task.client.ItunesClient;
import com.acme.task.model.itunes.EntityType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ItunesHealthIndicator extends AbstractHealthIndicator {

  final ItunesClient itunesClient;

  @Override
  protected void doHealthCheck(Builder builder) throws Exception {
    builder.withDetail("External service", "iTunes");
    String response = itunesClient.search("test", EntityType.ALBUM.getValue(), 1);
    if (FALLBACK_VALUE.equals(response)) {
      builder.down().withDetail("Error", "Fallback value was returned");
    } else {
      builder.up().withDetail("Test request response", response);
    }
  }

}
