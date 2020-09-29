package com.acme.task.model.properties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ConfigurationProperties(prefix = "async.async-executor")
@Validated
public class AsyncExecutorProperties {

  @Positive
  private int corePoolSize;

  @Positive
  private int maxPoolSize;

  @Positive
  private int queueCapacity;

  @NotBlank
  private String threadNamePrefix;

}
