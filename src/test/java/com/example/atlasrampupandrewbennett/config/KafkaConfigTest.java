package com.example.atlasrampupandrewbennett.config;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;

class KafkaConfigTest {

  @Test
  public void verifyAspectHooksupProperly() {
    ApplicationContext context = new AnnotationConfigApplicationContext(KafkaConfig.class);
    KafkaTemplate<String, String> kafkaTemplate = context.getBean(KafkaTemplate.class);
    Assertions.assertTrue(Objects.nonNull(kafkaTemplate));
  }
}
