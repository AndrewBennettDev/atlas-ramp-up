package com.example.atlasrampupandrewbennett;

import com.example.atlasrampupandrewbennett.models.GoogleFiles;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ScenarioContext {

  private GoogleFiles googleFiles;

}
