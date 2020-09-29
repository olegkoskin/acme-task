package com.acme.task.model.itunes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchCollection {

  String wrapperType;
  long artistId;
  long collectionId;
  String artistName;
  String collectionName;
  String collectionCensoredName;

}
