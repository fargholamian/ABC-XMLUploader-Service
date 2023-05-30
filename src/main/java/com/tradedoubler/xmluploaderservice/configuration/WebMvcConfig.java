package com.tradedoubler.xmluploaderservice.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

  private HandlerInterceptor authenticationService;
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authenticationService);
  }
}
