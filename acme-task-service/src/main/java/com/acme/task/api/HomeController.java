package com.acme.task.api;

import static org.springdoc.core.Constants.SWAGGER_UI_PATH;
import static org.springframework.web.servlet.view.UrlBasedViewResolver.REDIRECT_URL_PREFIX;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController {

  @Value(SWAGGER_UI_PATH)
  String swaggerUiPath;

  /**
   * Home redirection to swagger api documentation.
   */
  //@GetMapping(DEFAULT_PATH_SEPARATOR)
  public String index() {
    return REDIRECT_URL_PREFIX + swaggerUiPath;
  }

}
