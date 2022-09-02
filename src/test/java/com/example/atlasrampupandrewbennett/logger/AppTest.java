package com.example.atlasrampupandrewbennett.logger;

import static org.assertj.core.api.Assertions.assertThat;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;


public class AppTest {

  private ListAppender<ILoggingEvent> appender;
  private Logger appLogger = (Logger) LoggerFactory.getLogger(App.class);

  @BeforeEach
  public void setUp(){
    appender = new ListAppender<>();
    appender.start();
    appLogger.addAppender(appender);
  }

  @AfterEach
  public void tearDown(){
    appLogger.detachAppender(appender);
  }

  @Test
  public void testSayHi() {
    App.sayHi("Java");
    App.sayHi("Logback");

    assertThat(appender.list)
        .extracting(ILoggingEvent::getFormattedMessage)
        .containsExactly("Hi, Java!", "Hi, Logback!");
  }
}
