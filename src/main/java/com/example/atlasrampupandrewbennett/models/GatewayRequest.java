package com.example.atlasrampupandrewbennett.models;

import java.util.Map;
import lombok.Data;

@Data
public class GatewayRequest {

  private String tenantId;
  private String integrationId;
  private String domain;
  private String path;
  private String method;
  private Map<String, String> parametersMap;
}
