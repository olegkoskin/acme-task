package com.acme.task.api;

import com.acme.task.model.Creation;
import com.acme.task.service.CreationService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
public class CreationController {

  final CreationService creationService;

  /**
   * Fetch some books and some albums that are related to the input term.
   *
   * @param input search string
   * @return some books and some albums by search string
   */
  @Operation(summary = "Search the creations by input")
  @GetMapping(value = "/creations", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Creation> getCreations(@RequestParam String input) {
    return creationService.getCreations(input);
  }

}
