package com.tradedoubler.xmluploaderservice.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Getter
public class AppConfig implements WebMvcConfigurer {

  @Value("${upload.directory}")
  private String uploadDirectory;

  @Value("${authentication.get.user.uri}")
  private String getUserUri;

  private final Resource xsdResource = new ClassPathResource("files/Sample0.xsd");
}
