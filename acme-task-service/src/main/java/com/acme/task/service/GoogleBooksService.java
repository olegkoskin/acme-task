package com.acme.task.service;

import com.acme.task.client.GoogleBooksClient;
import com.acme.task.model.google.BooksVolumes;
import com.acme.task.model.google.PrintType;
import com.acme.task.model.google.Projection;
import com.acme.task.model.properties.GoogleBooksProperties;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class GoogleBooksService {

  final GoogleBooksProperties properties;
  final GoogleBooksClient googleBooksClient;

  /**
   * Get books volumes info from GoogleBooks.
   *
   * @param input search string
   * @return books volumes
   */
  @Async("asyncExecutor")
  public CompletableFuture<BooksVolumes> getBooks(String input) {
    log.debug("Retrieve google books volumes by search string : {} limit : {}",
        input, properties.getLimit());
    BooksVolumes bookVolumes = googleBooksClient.getBookVolumes(input, properties.getLimit(),
        PrintType.BOOKS, Projection.LITE);
    return CompletableFuture.completedFuture(bookVolumes);
  }

}
