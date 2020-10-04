package com.acme.task;

import java.lang.reflect.Method;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayNameGenerator;

/**
 * JUnit display name generator. It replaces a "camelCase" name
 * by "Capitalized Whitespace Separated" name.
 */
public class ReplaceCamelCase extends DisplayNameGenerator.Standard {

  @Override
  public String generateDisplayNameForClass(Class<?> testClass) {
    return replaceCapitals(super.generateDisplayNameForClass(testClass));
  }

  @Override
  public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
    return replaceCapitals(super.generateDisplayNameForNestedClass(nestedClass));
  }

  @Override
  public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
    String name = replaceCapitals(testMethod.getName());
    return testMethod.getParameterCount() > 0
        ? name + DisplayNameGenerator.parameterTypesAsString(testMethod)
        : name;

  }

  private String replaceCapitals(String name) {
    return StringUtils.capitalize(name.replaceAll("([A-Z])", " $1")
        .replaceAll("([0-9].)", " $1"));
  }

}
