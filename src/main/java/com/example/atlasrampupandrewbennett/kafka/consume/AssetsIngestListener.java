package com.example.atlasrampupandrewbennett.kafka.consume;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AssetsIngestListener {

  @KafkaListener(topics = "integrated-data", groupId = "atlas-ramp-up")
  public void newListener(String message) {
    log.info("KafkaListener received:" + message);
  }
}
