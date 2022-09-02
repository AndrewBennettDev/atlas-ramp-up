package com.example.atlasrampupandrewbennett.models;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GatewayRequestTest {

  ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void verifyGatewayRequestJson() throws JsonProcessingException {
    GatewayRequest gatewayRequest = new GatewayRequest();
    gatewayRequest.setTenantId("6ba920b2-098c-11ec-a73d-6f1aabf220db");
    gatewayRequest.setIntegrationId("6b6a425f-098c-11ec-8c5d-cdde4890d4d5");
    gatewayRequest.setDomain("googleapis.com/drive/v3");
    gatewayRequest.setPath("/files");
    gatewayRequest.setMethod("get");
    gatewayRequest.setParametersMap(new HashMap<>());
    String expectedGatewayRequestJson =
        "{\"tenantId\":\"6ba920b2-098c-11ec-a73d-6f1aabf220db\",\"integrationId\":\"6b6a425f-098c-11ec-8c5d-cdde4890d4d5\",\"domain\":\"googleapis.com/drive/v3\",\"path\":\"/files\",\"method\":\"get\",\"parametersMap\":{}}";
    String actualGatewayRequestJson = objectMapper.writeValueAsString(gatewayRequest);
    Assertions.assertEquals(expectedGatewayRequestJson, actualGatewayRequestJson);
  }

  @Test
  void verifyGatewayRequestDeserialization() throws JsonProcessingException {
    String rawGatewayRequestJson =
        "{\"tenantId\":\"6ba920b2-098c-11ec-a73d-6f1aabf220db\",\"integrationId\":\"6b6a425f-098c-11ec-8c5d-cdde4890d4d5\",\"domain\":\"googleapis.com/drive/v3\",\"path\":\"/files\",\"method\":\"get\",\"parametersMap\":{}}";
    GatewayRequest gatewayRequest =
        objectMapper.readValue(rawGatewayRequestJson, GatewayRequest.class);
    Assertions.assertEquals("6ba920b2-098c-11ec-a73d-6f1aabf220db", gatewayRequest.getTenantId());
    Assertions.assertEquals(
        "6b6a425f-098c-11ec-8c5d-cdde4890d4d5", gatewayRequest.getIntegrationId());
    Assertions.assertEquals("googleapis.com/drive/v3", gatewayRequest.getDomain());
    Assertions.assertEquals("/files", gatewayRequest.getPath());
    Assertions.assertEquals("get", gatewayRequest.getMethod());
    Assertions.assertEquals(new HashMap<>(), gatewayRequest.getParametersMap());
  }
}
