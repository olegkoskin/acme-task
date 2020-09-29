package com.acme.task.config;

import com.acme.task.model.properties.ExternalServicesUrlProperties;
import feign.Logger.Level;
import feign.Request;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Global Feign configuration.
 * All Feign related beans, which are specified here, also are applied to all Feign clients.
 * So, not need to specify this configuration in {@code FeignClient#configuration()}.
 * We specify some custom configuration for some specific Feing client in
 * {@code FeignClient#configuration()}
 */
@Configuration
public class FeignConfiguration {

  /**
   * <p>Create Feign logger level bean.</p>
   * <p>
   * The Logger.Level object that may be configured per client, tells Feign how much to log.
   * Choices are:
   * <ul>
   *   <li><b>NONE</b>, No logging (DEFAULT).</li>
   *   <li><b>BASIC</b>, Log only the request method and URL and the response status code and
   *   execution time.</li>
   *   <li><b>HEADERS</b>, Log the basic information along with request and response headers.</li>
   *   <li><b>FULL</b>, Log the headers, body, and metadata for both requests and responses.</li>
   * </ul>
   * </p>
   *
   * @return logger level
   */
  @Bean
  public Level feignLoggerLevel() {
    return Level.BASIC;
  }

  @Bean
  public Request.Options timeoutConfiguration(ExternalServicesUrlProperties properties) {
    return new Request.Options(properties.getConnectTimeout(), TimeUnit.MILLISECONDS,
        properties.getReadTimeout(), TimeUnit.MILLISECONDS, true);
  }

}

