package com.example.atlasrampupandrewbennett.controller;

import static org.mockito.Mockito.verify;

import com.example.atlasrampupandrewbennett.service.AssetsIngestService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GatewayControllerTest {

  private AssetsIngestService mockAssetsIngestService = Mockito.mock(AssetsIngestService.class);

  @Test
  void getUsersTest() {
    GatewayController gatewayController = new GatewayController(mockAssetsIngestService);
    gatewayController.getUsers();
    verify(mockAssetsIngestService).process();
  }
}
