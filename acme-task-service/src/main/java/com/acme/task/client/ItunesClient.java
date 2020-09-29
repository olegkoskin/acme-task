package com.acme.task.client;

import com.acme.task.client.ItunesClient.ItunesClientFallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ItunesClient", url = "${acm.external-services.itunes.url}",
    fallback = ItunesClientFallback.class)
public interface ItunesClient {

  @GetMapping(value = "/search", produces = "text/javascript; charset=utf-8")
  String search(@RequestParam String term, @RequestParam String entity, @RequestParam int limit);

  @Slf4j
  @Component
  class ItunesClientFallback implements ItunesClient {

    public static final String FALLBACK_VALUE = "{\n"
        + " \"resultCount\":0,\n"
        + " \"results\": []\n"
        + "}";

    @Override
    public String search(String term, String entity, int limit) {
      return FALLBACK_VALUE;
    }
  }
}
