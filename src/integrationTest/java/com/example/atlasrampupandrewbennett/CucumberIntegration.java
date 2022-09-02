package com.example.atlasrampupandrewbennett;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/integrationtest/resources/features",
    plugin = {"pretty", "html:build/cucumber", "json:build/cucumber.json"},
    glue = "com.example.atlasrampupandrewbennett")
public class CucumberIntegration {

  private CucumberIntegration() {}
}
