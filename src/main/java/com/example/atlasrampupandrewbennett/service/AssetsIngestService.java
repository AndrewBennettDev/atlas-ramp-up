package com.example.atlasrampupandrewbennett.service;

import com.example.atlasrampupandrewbennett.models.File;
import com.example.atlasrampupandrewbennett.models.GatewayRequest;
import com.example.atlasrampupandrewbennett.models.GoogleFiles;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class AssetsIngestService {

  static final Logger logger = LoggerFactory.getLogger(AssetsIngestService.class);

  public static final String topic = "integrated-data";
  private KafkaTemplate<String, String> kafkaTemplate;
  private RestTemplate restTemplate;
  ObjectMapper objectMapper;
  public static final String GATEWAY_URL = "/private/gateway/v1";
  private String gatewayDomain;

  @Autowired
  public AssetsIngestService(KafkaTemplate<String, String> kafkaTemplate, RestTemplate restTemplate, @Value("${gateway.domain:http://localhost:8080}") final String gatewayDomain) {
    this.kafkaTemplate = kafkaTemplate;
    this.restTemplate = restTemplate;
    this.objectMapper = new ObjectMapper();
    this.gatewayDomain = gatewayDomain;
  }

  public GoogleFiles process() {
    GoogleFiles googleFiles = getGoogleFiles();
    for (File file : googleFiles.getFiles()) {
      try {
        logger.info(objectMapper.writeValueAsString(file));
        kafkaTemplate.send(topic, objectMapper.writeValueAsString(file));
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }
    return googleFiles;
  }

  protected GoogleFiles getGoogleFiles() {
    GatewayRequest gatewayRequest = new GatewayRequest();
    gatewayRequest.setTenantId("tenant_id");
    gatewayRequest.setIntegrationId("integration_id");
    gatewayRequest.setDomain("googleapis.com/drive/v3");
    gatewayRequest.setPath("/files");
    gatewayRequest.setMethod("get");
    gatewayRequest.setParametersMap(new HashMap<>());
    GoogleFiles googleFiles =
        this.restTemplate.postForObject(
            gatewayDomain + GATEWAY_URL, gatewayRequest, GoogleFiles.class);
    return googleFiles;
  }
}
