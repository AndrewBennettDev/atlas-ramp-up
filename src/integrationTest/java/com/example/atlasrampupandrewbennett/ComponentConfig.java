package com.example.atlasrampupandrewbennett;

import com.example.atlasrampupandrewbennett.MockServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ComponentConfig {

  @Bean
  public MockServer mockServer(){
    return new MockServer();
  }

}
