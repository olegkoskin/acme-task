package com.acme.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableCircuitBreaker
@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.acme.task")
public class AcmeTaskApplication {

  public static void main(String[] args) {
    SpringApplication.run(AcmeTaskApplication.class, args);
  }

  /**
   * Custom Open API component for swagger-ui.
   *
   * @param appVersion project version
   * @return open api component
   */
  @Bean
  public OpenAPI customOpenApi(@Value("${springdoc.version}") String appVersion) {
    return new OpenAPI()
        .info(new Info().title("ACME Task APIs")
            .description("APIs to support ACME Task")
            .version(appVersion));
  }

  @Bean
  @Primary
  public ModelResolver modelResolver(ObjectMapper objectMapper) {
    return new ModelResolver(objectMapper);
  }

  /**
   * Crate CORS filter for ui app.
   *
   * @return FilterRegistrationBean of CorsFilter
   */
  @Bean
  public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.setAllowedOrigins(List.of("http://localhost:4200"));
    config.setAllowedHeaders(List.of("*"));
    config.setAllowedMethods(List.of("*"));

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);

    FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
    bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return bean;
  }

}
