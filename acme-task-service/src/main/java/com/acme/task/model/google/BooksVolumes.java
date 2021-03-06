package com.acme.task.model.google;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BooksVolumes {

  @Builder.Default
  List<BookVolume> items = List.of();

}
