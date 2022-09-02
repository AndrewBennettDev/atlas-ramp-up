package com.example.atlasrampupandrewbennett.stepdefinitions;

import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.awaitility.Awaitility.await;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.example.atlasrampupandrewbennett.AssetsIngestTestListener;
import com.example.atlasrampupandrewbennett.ScenarioContext;
import com.example.atlasrampupandrewbennett.MockServer;
import com.example.atlasrampupandrewbennett.kafka.consume.AssetsIngestListener;
import com.example.atlasrampupandrewbennett.models.GoogleFiles;
import com.example.atlasrampupandrewbennett.service.AssetsIngestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class GetRequest {

  @Autowired private ScenarioContext scenarioContext;
  @Autowired private MockServer mockServer;
  @Autowired private RestTemplate restTemplate;
  @Autowired private AssetsIngestTestListener testListener;

  private ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
  private ObjectMapper objectMapper = new ObjectMapper();

  @Value("${local.server.port}")
  private String randomPort;

  @Given("An IVR-Gateway endpoint")
  public void localEndpointActivity() throws IOException {
    File resource = new ClassPathResource("jsonfiles/filesResponse.json").getFile();
    String fileResponse = Files.readString(resource.toPath());
    mockServer.startWireMock();
    stubFor(
        post(AssetsIngestService.GATEWAY_URL)
            .willReturn(
                ok().withHeader("Content-Type", "application/json")
                    .withBody(fileResponse)));
  }

  @When("A GET request is made to rampUpServer\\/fetch\\/googleUsers")
  public void getRequestMade(){
    Logger kafkaListenerLogger = (Logger) LoggerFactory.getLogger(AssetsIngestListener.class);
    listAppender.start();
    kafkaListenerLogger.addAppender(listAppender);

    GoogleFiles fileList = this.restTemplate.getForObject("http://localhost:" + randomPort + "/fetch/googleUsers", GoogleFiles.class);
    scenarioContext.setGoogleFiles(fileList);
  }

  @Then("A List of JSON String data is produced to the kafka topic")
  public void jsonReturnedToKafka() throws JsonProcessingException {

    GoogleFiles googleFiles = scenarioContext.getGoogleFiles();
    List<com.example.atlasrampupandrewbennett.models.File> expectedFileList = googleFiles.getFiles();
    await().until(() -> testListener.getListenerQueue().size() == expectedFileList.size());

    ConcurrentLinkedQueue<String> testQueue = testListener.getListenerQueue();
    ArrayList<com.example.atlasrampupandrewbennett.models.File> testList = new ArrayList<>();

    for(String message : testQueue){
      com.example.atlasrampupandrewbennett.models.File file = objectMapper.readValue(message,
          com.example.atlasrampupandrewbennett.models.File.class);
      testList.add(file);
    }

    Assertions.assertTrue(expectedFileList.containsAll(testList) && testList.containsAll(expectedFileList));
  }

  @Then("A List of JSON String data is returned to the caller")
  public void jsonReturnedToCaller() throws IOException {
    GoogleFiles fileList = scenarioContext.getGoogleFiles();

    File resource = new ClassPathResource("jsonfiles/filesResponse.json").getFile();
    String fileResponse = Files.readString(resource.toPath());

    GoogleFiles responseObject = objectMapper.readValue(fileResponse, GoogleFiles.class);

    Assertions.assertEquals(responseObject, fileList);

  }

  @Then("A List of JSON String data is consumed by the Kafka listener")
  public void aListOfJSONStringDataIsConsumedByTheKafkaListener() throws JsonProcessingException {

    GoogleFiles googleFiles = scenarioContext.getGoogleFiles();
    List<com.example.atlasrampupandrewbennett.models.File> expectedFileList = googleFiles.getFiles();

    List<ILoggingEvent> kafkaListenerList = listAppender.list;
    ArrayList<com.example.atlasrampupandrewbennett.models.File> testListenerList = new ArrayList<>();

    for(ILoggingEvent iLoggingEvent : kafkaListenerList) {
      String fullMessage = iLoggingEvent.getFormattedMessage();
      String finalMessage = fullMessage.substring(23);
      System.out.println(finalMessage);
      com.example.atlasrampupandrewbennett.models.File file = objectMapper.readValue(finalMessage,
          com.example.atlasrampupandrewbennett.models.File.class);
      testListenerList.add(file);
    }

    Assertions.assertTrue(testListenerList.containsAll(expectedFileList) && expectedFileList.containsAll(testListenerList));

  }
}
