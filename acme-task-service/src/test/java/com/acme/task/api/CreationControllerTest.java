package com.acme.task.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.acme.task.ReplaceCamelCase;
import com.acme.task.config.ExternalServersStubsConfiguration;
import com.acme.task.model.Creation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.UriComponentsBuilder;

@DisplayNameGeneration(ReplaceCamelCase.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
    ExternalServersStubsConfiguration.class
})
@AutoConfigureMockMvc
@ActiveProfiles("it")
class CreationControllerTest {

  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;

  @Test
  void shouldSearchCreationsForSdfsdf() throws Exception {
    // given
    URI endpointUri = UriComponentsBuilder.fromUriString("/v1/creations").
        queryParam("input", "sdfsdf")
        .buildAndExpand().toUri();

    // when
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(endpointUri))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();

    // then
    assertEqualResponse(mvcResult, "responses/creations-sdfsdf-5.json");
  }

  @Test
  void shouldSearchCreationsForHello() throws Exception {
    // given
    URI endpointUri = UriComponentsBuilder.fromUriString("/v1/creations").
        queryParam("input", "hello")
        .buildAndExpand().toUri();

    // when
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(endpointUri))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andReturn();

    // then
    assertEqualResponse(mvcResult, "responses/creations-hello-5.json");
  }

  private void assertEqualResponse(MvcResult mvcResult, String path)
      throws JsonProcessingException, UnsupportedEncodingException {
    TypeReference<List<Creation>> valueTypeRef = new TypeReference<>() {};
    List<Creation> actual = getActualResponse(mvcResult, valueTypeRef);
    List<Creation> expected = readExpectedResponse(path, valueTypeRef);
    assertThat(actual)
        .hasSize(expected.size())
        .isEqualTo(expected);
  }

  private <T> T getActualResponse(MvcResult mvcResult, TypeReference<T> valueTypeRef)
      throws JsonProcessingException, UnsupportedEncodingException {
    return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), valueTypeRef);
  }

  private <T> T readExpectedResponse(String path, TypeReference<T> valueTypeRef) {
    try {
      String json = IOUtils.toString(new ClassPathResource(path).getInputStream(),
          StandardCharsets.UTF_8.displayName());
      return objectMapper.readValue(json, valueTypeRef);
    } catch (IOException e) {
      throw new IllegalStateException("Can't read file", e);
    }
  }

}