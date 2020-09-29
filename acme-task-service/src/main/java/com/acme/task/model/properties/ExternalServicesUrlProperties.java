package com.acme.task.model.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
@ConfigurationProperties(prefix = "acm.external-services")
public class ExternalServicesUrlProperties {

  long readTimeout;
  long connectTimeout;

}
