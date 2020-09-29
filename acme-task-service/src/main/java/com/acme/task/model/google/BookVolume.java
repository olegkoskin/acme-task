package com.acme.task.model.google;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookVolume {

  String kind;
  String id;
  String etag;
  String selfLink;
  BookVolumeInfo volumeInfo;

}
