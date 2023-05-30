package com.tradedoubler.xmluploaderservice.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.tradedoubler.xmluploaderservice.BaseServiceTest;
import com.tradedoubler.xmluploaderservice.configuration.AppConfig;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

public class XmlServiceTest extends BaseServiceTest {
  private final AppConfig appConfig = new AppConfig();

  private final XmlService xmlService = new XmlService(appConfig);

  @Test
  public void shouldValidateReturnTrue_WhenXmlFileIsValid() throws IOException {
    InputStream inputStream = new ClassPathResource("files/ValidXMLFile.xml").getInputStream();
    Boolean result = xmlService.validate(inputStream);

    assertThat(result).isTrue();
  }

  @Test
  public void shouldValidateReturnFalse_WhenXmlFileIsInValid() throws IOException {
    InputStream inputStream = new ClassPathResource("files/InValidXMLFile.xml").getInputStream();
    Boolean result = xmlService.validate(inputStream);

    assertThat(result).isFalse();
  }

}
