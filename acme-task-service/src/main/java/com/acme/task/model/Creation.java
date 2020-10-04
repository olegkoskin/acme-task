package com.acme.task.model;

import com.acme.task.model.google.BookVolumeInfo;
import com.acme.task.model.itunes.SearchCollection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Creation {

  String title;
  List<String> creators;
  CreationType type;

  /**
   * Create Creation dto from GoogleBooks BookVolumeInfo.
   *
   * @param volume book volume info
   * @return creation dto
   */
  public static Creation of(BookVolumeInfo volume) {
    List<String> creators;
    if (volume.getAuthors() != null) {
      creators = volume.getAuthors();
    } else if (volume.getPublisher() != null) {
      creators = List.of(volume.getPublisher());
    } else {
      creators = List.of();
    }
    return Creation.builder()
        .title(volume.getTitle())
        .creators(creators)
        .type(CreationType.BOOK)
        .build();
  }

  /**
   * Create Creation dto from iTunes search collection dto.
   *
   * @param collection iTunes search collection dto
   * @return creation dto
   */
  public static Creation of(SearchCollection collection) {
    return Creation.builder()
        .title(collection.getCollectionName())
        .creators(List.of(collection.getArtistName()))
        .type(CreationType.ALBUM)
        .build();
  }

}
