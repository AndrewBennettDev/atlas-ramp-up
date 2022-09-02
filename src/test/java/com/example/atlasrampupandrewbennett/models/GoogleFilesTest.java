package com.example.atlasrampupandrewbennett.models;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GoogleFilesTest {

  ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void verifyGoogleFilesJson() throws JsonProcessingException {
    File file = new File();
    file.setKind("drive#file");
    file.setId("1Vq58as3BNt-UjChrO5_G15jOy6O4Iv2H_iBWBBrG3go");
    file.setName("JoshA Test");
    file.setMimeType("application/vnd.google-apps.document");

    List<File> mockList = new ArrayList();
    mockList.add(file);

    GoogleFiles googleFiles = new GoogleFiles();
    googleFiles.setKind("drive#fileList");
    googleFiles.setNextPageToken(
        "~!!~AI9FV7SEqedEZys0OeflV_N_cpzRKy3e_a5d5iwnM9kiwKu_iv173xfDgpU_BvQRa14uP0kzAgcGX-B_lAokDLCX69ySH8FYfo6KmlDydA8m_kEt-j_cFYYSr_w4_1aj2OSRkycL9RdG0VSjQnqf19RSKUhccCvdBuoZGvHIJRA6ovSrJnfVj8HMRQAZpLONP6Tze2N3Isb0apU4epSztmlaSZeV42n-L0jTKNnjbF8Rs9GE63_wNsWolGmyfrzLjuJGS8YllNyaFb-8PfFA5Q47Ynv0qUnSCwssf2R2C15U_pIBD78iTqeQJnu10m_815WkKdJHVC4r");
    googleFiles.setIncompleteSearch(false);
    googleFiles.setFiles(mockList);

    String expectedGoogleFilesJson =
        "{\"kind\":\"drive#fileList\",\"nextPageToken\":\"~!!~AI9FV7SEqedEZys0OeflV_N_cpzRKy3e_a5d5iwnM9kiwKu_iv173xfDgpU_BvQRa14uP0kzAgcGX-B_lAokDLCX69ySH8FYfo6KmlDydA8m_kEt-j_cFYYSr_w4_1aj2OSRkycL9RdG0VSjQnqf19RSKUhccCvdBuoZGvHIJRA6ovSrJnfVj8HMRQAZpLONP6Tze2N3Isb0apU4epSztmlaSZeV42n-L0jTKNnjbF8Rs9GE63_wNsWolGmyfrzLjuJGS8YllNyaFb-8PfFA5Q47Ynv0qUnSCwssf2R2C15U_pIBD78iTqeQJnu10m_815WkKdJHVC4r\",\"incompleteSearch\":false,\"files\":[{\"kind\":\"drive#file\",\"id\":\"1Vq58as3BNt-UjChrO5_G15jOy6O4Iv2H_iBWBBrG3go\",\"name\":\"JoshA Test\",\"mimeType\":\"application/vnd.google-apps.document\"}]}";
    String actualGoogleFilesJson = objectMapper.writeValueAsString(googleFiles);
    Assertions.assertEquals(expectedGoogleFilesJson, actualGoogleFilesJson);
  }
}
