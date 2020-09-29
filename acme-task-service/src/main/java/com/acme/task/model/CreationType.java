package com.acme.task.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CreationType {

  @JsonProperty("book")
  BOOK,
  @JsonProperty("album")
  ALBUM;

}
