package com.example.atlasrampupandrewbennett;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.atlasrampupandrewbennett.kafka.consume.AssetsIngestListener;
import com.example.atlasrampupandrewbennett.models.File;
import com.example.atlasrampupandrewbennett.service.AssetsIngestService;
import io.cucumber.spring.CucumberContextConfiguration;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "post=9092"})
public class CucumberSpringConfig {

}
