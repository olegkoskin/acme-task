package com.acme.task.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
public class SpringDocConfiguration {

  /**
   * Define OpenApi group for actuator endpoint. Only for non-prod.
   *
   * @return actuator GroupedOpenApi
   */
  @Bean
  @Profile("!prod")
  public GroupedOpenApi actuatorApi() {
    return GroupedOpenApi.builder().group("Actuator")
        .pathsToMatch("/actuator/**")
        .pathsToExclude("/actuator/health/*")
        .build();
  }

  /**
   * Custom OpenAPI component for swagger-ui.
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

  /**
   * Use same ObjectMapper in app and in swagger.
   *
   * @param objectMapper object mapper
   * @return swagger ModelResolver
   */
  @Bean
  @Primary
  public ModelResolver modelResolver(ObjectMapper objectMapper) {
    return new ModelResolver(objectMapper);
  }

}
