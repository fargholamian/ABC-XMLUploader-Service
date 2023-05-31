package com.tradedoubler.xmluploaderservice.service;

import com.tradedoubler.xmluploaderservice.configuration.AppConfig;
import com.tradedoubler.xmluploaderservice.model.User;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthenticationService {

  Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private AppConfig appConfig;

  public Optional<User> getUser(String token) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);

    HttpEntity<?> entity = new HttpEntity<HttpHeaders>(null, headers);

    try {
      logger.info("Auth Server URI: " + appConfig.getGetUserUri());
      ResponseEntity<User> response =
          restTemplate.exchange(appConfig.getGetUserUri(), HttpMethod.GET, entity, User.class);
      if (response.getStatusCode() == HttpStatus.OK
          && response.getBody() != null) {
        return Optional.of(response.getBody());
      }
    }  catch (HttpStatusCodeException exception) {
      return Optional.empty();
    }
    return Optional.empty();
  }
}
