package com.acme.task.api;

import com.acme.task.model.Creation;
import com.acme.task.service.CreationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/v1/creators")
public class CreatorsController {

  final CreationService creationService;

  /**
   * Fetch some books and some albums that are related to the input term.
   *
   * @param input search string
   * @return some books and some albums by search string
   */
  @GetMapping(value = "/creations", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Creation> getCreations(@RequestParam String input) {
    return creationService.getCreations(input);
  }

  @GetMapping(value = "/creations-all", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Creation> getCreations() {
    return creationService.getCreations("all");
  }

}
