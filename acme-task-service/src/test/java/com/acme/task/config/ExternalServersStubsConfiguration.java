package com.acme.task.config;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.common.SingleRootFileSource;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.standalone.JsonFileMappingsSource;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;

@TestConfiguration
@ActiveProfiles("it")
public class ExternalServersStubsConfiguration {

  private static final String FILE_SOURCE = "src/test/resources/external-services-bodies";
  private static final String MAPPING_SOURCE = "src/test/resources/external-services-stubs";

  private static WireMockServer server;

  @PostConstruct
  public void initSecurityServer() {
    server = new WireMockServer(options()
        .port(8089)
        .notifier(new ConsoleNotifier(true))
        .fileSource(new SingleRootFileSource(FILE_SOURCE))
        .mappingSource(new JsonFileMappingsSource(new SingleRootFileSource(MAPPING_SOURCE)))
        .extensions(new ResponseTemplateTransformer(false)));

    server.start();

    WireMock.configureFor("localhost", 8089);
  }

  @PreDestroy
  public void stopSecurityServer() {
    server.stop();
  }

}
