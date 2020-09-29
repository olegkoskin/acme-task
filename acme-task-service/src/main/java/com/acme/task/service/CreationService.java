package com.acme.task.service;

import com.acme.task.model.Creation;
import com.acme.task.model.google.BookVolume;
import com.acme.task.model.google.BooksVolumes;
import com.acme.task.model.itunes.SearchCollections;
import com.acme.task.model.properties.ExternalServicesUrlProperties;
import com.acme.task.util.FutureUtils;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreationService {

  private static final String FUTURE_ERROR_MESSAGE = "Unable to obtain albums/books data. Input %s";

  final ExternalServicesUrlProperties properties;
  final GoogleBooksService googleBooksService;
  final ItunesService itunesService;

  /**
   * Retrieve the creations by search string.
   *
   * @param input search string
   * @return list of creations
   */
  public List<Creation> getCreations(String input) {
    log.debug("Retrieve the creations by search string : {}", input);
    if (StringUtils.isBlank(input)) {
      return List.of();
    }

    CompletableFuture<BooksVolumes> booksFuture = googleBooksService.getBooks(input);
    CompletableFuture<SearchCollections> albumsFuture = itunesService.getAlbums(input);

    CompletableFuture<Void> allFutures = CompletableFuture.allOf(booksFuture, albumsFuture);
    FutureUtils.getSafety(allFutures, properties.getReadTimeout(),
        String.format(FUTURE_ERROR_MESSAGE, input));

    Stream<Creation> bookStream = booksFuture.join().getItems().stream()
        .map(BookVolume::getVolumeInfo)
        .map(Creation::of);
    Stream<Creation> albumStream = albumsFuture.join().getResults().stream()
        .map(Creation::of);

    return Stream.concat(bookStream, albumStream)
        .sorted(Comparator.comparing(Creation::getTitle))
        .collect(Collectors.toList());
  }

}
