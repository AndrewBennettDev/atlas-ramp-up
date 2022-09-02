package com.example.atlasrampupandrewbennett.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.example.atlasrampupandrewbennett.models.File;
import com.example.atlasrampupandrewbennett.models.GatewayRequest;
import com.example.atlasrampupandrewbennett.models.GoogleFiles;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

class AssetsIngestServiceTest {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private RestTemplate restTemplate;
  private AssetsIngestService assetsIngestService;

  public AssetsIngestServiceTest() {
    this.restTemplate = Mockito.mock(RestTemplate.class);
    this.kafkaTemplate = Mockito.mock(KafkaTemplate.class);
    this.assetsIngestService = new AssetsIngestService(kafkaTemplate, restTemplate, "gatewayDomain");
  }

  @Test
  void processTest() {
    Logger newLogger = (Logger) LoggerFactory.getLogger(AssetsIngestService.class);
    ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
    listAppender.start();
    newLogger.addAppender(listAppender);

    GoogleFiles googleFiles = generateGoogleFiles();
    Mockito.doReturn(googleFiles)
        .when(restTemplate)
        .postForObject(Mockito.anyString(), Mockito.any(GatewayRequest.class), Mockito.any());

    AssetsIngestService service = new AssetsIngestService(kafkaTemplate, restTemplate, "gatewayDomain");
    GoogleFiles response = service.process();

    Assertions.assertEquals(1, response.getFiles().size());
    Assertions.assertTrue(googleFiles.getFiles().containsAll(response.getFiles()) && response.getFiles().containsAll(googleFiles.getFiles()));
    // test kind and nextpagetoken
    List<ILoggingEvent> logsList = listAppender.list;
    assertEquals(
        "{\"kind\":\"drive#file\",\"id\":\"1Vq58as3BNt-UjChrO5_G15jOy6O4Iv2H_iBWBBrG3go\",\"name\":\"JoshA Test\",\"mimeType\":\"application/vnd.google-apps.document\"}",
        logsList.get(0).getMessage());
    assertEquals(Level.INFO, logsList.get(0).getLevel());
  }

  @Test
  void verifyGetGoogleFiles() {
    GoogleFiles googleFiles = generateGoogleFiles();
    Mockito.doReturn(googleFiles)
        .when(restTemplate)
        .postForObject(Mockito.anyString(), Mockito.any(GatewayRequest.class), Mockito.any());
    ;
    AssetsIngestService service = new AssetsIngestService(kafkaTemplate, restTemplate, "gatewayDomain");
    GoogleFiles mockFiles = service.getGoogleFiles();
    System.out.println(mockFiles);
  }

  public GoogleFiles generateGoogleFiles() {
    File file = getFile();
    List<File> mockList = new ArrayList();
    mockList.add(file);
    GoogleFiles googleFiles = new GoogleFiles();
    googleFiles.setKind("drive#fileList");
    googleFiles.setNextPageToken(
        "~!!~AI9FV7SEqedEZys0OeflV_N_cpzRKy3e_a5d5iwnM9kiwKu_iv173xfDgpU_BvQRa14uP0kzAgcGX-B_lAokDLCX69ySH8FYfo6KmlDydA8m_kEt-j_cFYYSr_w4_1aj2OSRkycL9RdG0VSjQnqf19RSKUhccCvdBuoZGvHIJRA6ovSrJnfVj8HMRQAZpLONP6Tze2N3Isb0apU4epSztmlaSZeV42n-L0jTKNnjbF8Rs9GE63_wNsWolGmyfrzLjuJGS8YllNyaFb-8PfFA5Q47Ynv0qUnSCwssf2R2C15U_pIBD78iTqeQJnu10m_815WkKdJHVC4r");
    googleFiles.setIncompleteSearch(false);
    googleFiles.setFiles(mockList);
    return googleFiles;
  }

  private File getFile() {
    File file = new File();
    file.setKind("drive#file");
    file.setId("1Vq58as3BNt-UjChrO5_G15jOy6O4Iv2H_iBWBBrG3go");
    file.setName("JoshA Test");
    file.setMimeType("application/vnd.google-apps.document");
    return file;
  }
}
