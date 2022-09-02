package com.example.atlasrampupandrewbennett.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileTest {

  ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void verifyFileJson() throws JsonProcessingException {
    File file = new File();
    file.setKind("drive#file");
    file.setId("1Vq58as3BNt-UjChrO5_G15jOy6O4Iv2H_iBWBBrG3go");
    file.setName("JoshA Test");
    file.setMimeType("application/vnd.google-apps.document");
    String expectedFileJson =
        "{\"kind\":\"drive#file\",\"id\":\"1Vq58as3BNt-UjChrO5_G15jOy6O4Iv2H_iBWBBrG3go\",\"name\":\"JoshA Test\",\"mimeType\":\"application/vnd.google-apps.document\"}";
    String actualFileJson = objectMapper.writeValueAsString(file);
    Assertions.assertEquals(expectedFileJson, actualFileJson);
  }

  @Test
  void verifyFileDeserialization() throws JsonProcessingException {
    String rawFileJson =
        "{\"kind\":\"drive#file\",\"id\":\"1Vq58as3BNt-UjChrO5_G15jOy6O4Iv2H_iBWBBrG3go\",\"name\":\"JoshA Test\",\"mimeType\":\"application/vnd.google-apps.document\"}";
    File file = objectMapper.readValue(rawFileJson, File.class);
    Assertions.assertEquals("drive#file", file.getKind());
    Assertions.assertEquals("1Vq58as3BNt-UjChrO5_G15jOy6O4Iv2H_iBWBBrG3go", file.getId());
    Assertions.assertEquals("JoshA Test", file.getName());
    Assertions.assertEquals("application/vnd.google-apps.document", file.getMimeType());
  }
}
