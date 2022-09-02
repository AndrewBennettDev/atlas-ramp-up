package com.example.atlasrampupandrewbennett;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import com.github.tomakehurst.wiremock.WireMockServer;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

public class MockServer {

  private static final int PORT_NUMBER = 9999;
  public static final String WIREMOCK_HOST = "http://localhost:" + PORT_NUMBER;

  private final WireMockServer wireMockServer = new WireMockServer(
          options().port(PORT_NUMBER).withRootDirectory("src/integrationtest/resources/jsonfiles"));

  @PostConstruct
  public void startWireMock() {
    wireMockServer.start();
    configureFor("localhost", wireMockServer.port());
  }

  @PreDestroy
  public void stopWireMock() {
    wireMockServer.stop();
    wireMockServer.shutdown();
  }

  public String getUrl(){
    return this.wireMockServer.baseUrl();
  }
}
