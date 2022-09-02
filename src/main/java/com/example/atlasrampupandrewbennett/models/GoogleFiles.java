package com.example.atlasrampupandrewbennett.models;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoogleFiles {

  private String kind;
  private String nextPageToken;
  private Boolean incompleteSearch;
  private List<File> files;
}
