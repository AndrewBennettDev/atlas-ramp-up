package com.example.atlasrampupandrewbennett;

//import com.bettercloud.logging.constants.MDCKeys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AtlasRampUpAndrewBennettApplication {

  public static void main(String[] args) {
//    System.setProperty(MDCKeys.MICROSERVICE_NAME, "atlas-ramp-up-andrew-bennett");
//    System.setProperty(MDCKeys.PILLAR, "ingestion");
    SpringApplication.run(AtlasRampUpAndrewBennettApplication.class, args);
  }
}
