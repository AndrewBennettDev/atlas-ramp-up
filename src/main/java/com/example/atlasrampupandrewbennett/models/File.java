package com.example.atlasrampupandrewbennett.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class File {

  private String kind;
  private String id;
  private String name;
  private String mimeType;
}
