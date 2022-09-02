package com.example.atlasrampupandrewbennett.controller;

import com.example.atlasrampupandrewbennett.models.File;
import com.example.atlasrampupandrewbennett.models.GoogleFiles;
import com.example.atlasrampupandrewbennett.service.AssetsIngestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class GatewayController {

  private final AssetsIngestService assetsIngestService;

  @Autowired
  public GatewayController(AssetsIngestService assetsIngestService) {
    this.assetsIngestService = assetsIngestService;
  }

  @GetMapping("/fetch/googleUsers")
  public GoogleFiles getUsers() {
    return assetsIngestService.process();
  }
}
