package com.tradedoubler.xmluploaderservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tradedoubler.xmluploaderservice.configuration.AuthenticationInterceptor;
import com.tradedoubler.xmluploaderservice.enums.Role;
import com.tradedoubler.xmluploaderservice.model.User;
import com.tradedoubler.xmluploaderservice.service.EventProducer;
import com.tradedoubler.xmluploaderservice.service.XmlService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(FileUploadController.class)
@AutoConfigureMockMvc(addFilters = false)
public class FileUploadControllerTest {
  @Autowired
  MockMvc mockMvc;

  @MockBean
  AuthenticationInterceptor interceptor;

  @MockBean
  private XmlService xmlService;

  @MockBean
  private EventProducer eventProducer;

  private final User user = new User(UUID.randomUUID(), "user", "password" , Role.ROLE_USER);

  private final  MockMultipartFile file = new MockMultipartFile("file", "test.txt", MediaType.MULTIPART_FORM_DATA_VALUE, "<result></result>".getBytes());

  @Test
  public void shouldUploadAPIReturn200_WhenXmlIsValidAndFileIsStoredSuccessfully() throws Exception {
    when(interceptor.preHandle(any(), any(), any())).thenReturn(true);
    when(xmlService.validate(any())).thenReturn(Boolean.TRUE);
    when(xmlService.store(any())).thenReturn("filename");
    doNothing().when(eventProducer).sendEvent(any());

    MockMultipartHttpServletRequestBuilder multipartRequest =
        MockMvcRequestBuilders.multipart("/api/upload");

    mockMvc.perform(multipartRequest
            .file(file)
            .file(file)
            .file(file)
            .requestAttr("user", user)
        )
        .andExpect(status().isOk());
  }

  @Test
  public void shouldUploadAPIReturn500_WhenXmlIsNotValid() throws Exception {
    when(interceptor.preHandle(any(), any(), any())).thenReturn(true);
    when(xmlService.validate(any())).thenReturn(Boolean.FALSE);

    MockMultipartHttpServletRequestBuilder multipartRequest =
        MockMvcRequestBuilders.multipart("/api/upload");

    mockMvc.perform(multipartRequest
            .file(file)
            .requestAttr("user", user)
        )
        .andExpect(status().isInternalServerError());
  }

  @Test
  public void shouldUploadAPIReturn500_WhenXmlIsValidButStoringFailed() throws Exception {
    when(interceptor.preHandle(any(), any(), any())).thenReturn(true);
    when(xmlService.validate(any())).thenReturn(Boolean.TRUE);
    when(xmlService.store(any())).thenThrow(new RuntimeException("Something went wrong when storing xml file"));
    doNothing().when(eventProducer).sendEvent(any());

    MockMultipartHttpServletRequestBuilder multipartRequest =
        MockMvcRequestBuilders.multipart("/api/upload");

    mockMvc.perform(multipartRequest
            .file(file)
            .requestAttr("user", user)
        )
        .andExpect(status().isInternalServerError());
  }
}
