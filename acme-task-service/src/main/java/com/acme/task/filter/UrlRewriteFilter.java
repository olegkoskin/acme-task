package com.acme.task.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

//@Component
public class UrlRewriteFilter implements Filter {

  private static final String API_CONTEXT_PATH = "/api/";
  private static final String ACTUATOR_CONTEXT_PATH = "/actuator";

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest servletRequest = (HttpServletRequest) request;
    String requestUri = servletRequest.getRequestURI();
    String contextPath = servletRequest.getContextPath();
    if (!requestUri.equals(contextPath)
        && requestUri.startsWith(API_CONTEXT_PATH)
        && !requestUri.startsWith(ACTUATOR_CONTEXT_PATH)) {
      RequestDispatcher dispatcher = request.getRequestDispatcher("/");
      dispatcher.forward(request, response);
      return;
    }
    chain.doFilter(request, response);
  }

}
