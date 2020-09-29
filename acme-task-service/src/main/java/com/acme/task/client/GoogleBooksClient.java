package com.acme.task.client;

import com.acme.task.client.GoogleBooksClient.GoogleBooksClientFallback;
import com.acme.task.model.google.BooksVolumes;
import com.acme.task.model.google.PrintType;
import com.acme.task.model.google.Projection;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "GoogleBooksClient", url = "${acm.external-services.google-books.url}",
    fallback = GoogleBooksClientFallback.class)
public interface GoogleBooksClient {

  @GetMapping(value = "/v1/volumes", produces = MediaType.APPLICATION_JSON_VALUE)
  BooksVolumes getBookVolumes(@RequestParam String q, @RequestParam int maxResults,
      @RequestParam PrintType printType, @RequestParam Projection projection);

  @Component
  class GoogleBooksClientFallback implements GoogleBooksClient {

    public static final BooksVolumes FALLBACK_VALUE = BooksVolumes.builder().build();

    @Override
    public BooksVolumes getBookVolumes(String q, int maxResults, PrintType printType,
        Projection projection) {
      return FALLBACK_VALUE;
    }
  }
}
