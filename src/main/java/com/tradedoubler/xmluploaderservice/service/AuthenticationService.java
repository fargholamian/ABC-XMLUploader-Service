package com.tradedoubler.xmluploaderservice.service;

import com.tradedoubler.xmluploaderservice.controller.FileUploadController;
import com.tradedoubler.xmluploaderservice.entity.User;
import com.tradedoubler.xmluploaderservice.service.resp.GetUserResponse;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

  Logger logger = LoggerFactory.getLogger(FileUploadController.class);

  @Value("${authentication.get.user.uri}")
  private String getUserUri;

  private final RestTemplate restTemplate = new RestTemplate();

  public Optional<User> getUser(String token) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);

    HttpEntity<?> entity = new HttpEntity<HttpHeaders>(null, headers);

    try {
      logger.info("Auth Server URI: " + getUserUri);
      ResponseEntity<GetUserResponse> response =
          restTemplate.exchange(getUserUri, HttpMethod.GET, entity, GetUserResponse.class);
      if (response.getStatusCode() == HttpStatus.OK
          && response.getBody() != null
          && Boolean.TRUE.equals(response.getBody().getStatus())
          && response.getBody().getUser() != null) {
        return Optional.of(response.getBody().getUser());
      }
    }  catch (HttpStatusCodeException exception) {
      return Optional.empty();
    }

    return Optional.empty();
  }

}
