package com.acme.task.service;

import com.acme.task.client.ItunesClient;
import com.acme.task.exception.DataRetrievingException;
import com.acme.task.model.itunes.EntityType;
import com.acme.task.model.itunes.SearchCollections;
import com.acme.task.model.properties.ItunesProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ItunesService {

  final ItunesProperties properties;
  final ObjectMapper objectMapper;
  final ItunesClient itunesClient;

  /**
   * Get albums info from iTunes.
   *
   * @param input search string
   * @return album search collection
   */
  @Async("asyncExecutor")
  public CompletableFuture<SearchCollections> getAlbums(String input) {
    log.debug("Retrieve itunes albums by search string : {} limit : {}",
        input, properties.getLimit());
    String response = itunesClient
        .search(input, EntityType.ALBUM.getValue(), properties.getLimit());
    SearchCollections collections = parseResponse(response);
    return CompletableFuture.completedFuture(collections);
  }

  private SearchCollections parseResponse(String response) {
    try {
      return objectMapper.readValue(response, SearchCollections.class);
    } catch (JsonProcessingException e) {
      throw new DataRetrievingException("Unable to parse iTunes Search response", e);
    }
  }

}
