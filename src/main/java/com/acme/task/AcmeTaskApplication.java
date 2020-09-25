package com.acme.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

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

}
