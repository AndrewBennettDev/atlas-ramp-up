package com.example.atlasrampupandrewbennett.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

  private static final Logger logger = LoggerFactory.getLogger(App.class);

  public static void sayHi(String username) {
    logger.info("Hi, {}!", username);
  }
}
