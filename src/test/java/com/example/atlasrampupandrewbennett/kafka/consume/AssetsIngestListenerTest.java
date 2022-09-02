package com.example.atlasrampupandrewbennett.kafka.consume;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
class AssetsIngestListenerTest {

  @Autowired
  AssetsIngestListener testIngestListener = new AssetsIngestListener();

  public String message = "This is a test message.";

  @Test
  void newListener() {
    Logger assetsIngestListenerLogger = (Logger) LoggerFactory.getLogger(AssetsIngestListener.class);
    ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
    listAppender.start();
    assetsIngestListenerLogger.addAppender(listAppender);

    testIngestListener.newListener(message);
    List<ILoggingEvent> testListenerList = listAppender.list;

    Assertions.assertEquals("KafkaListener received:" + message, testListenerList.get(0).getMessage());
  }
}
