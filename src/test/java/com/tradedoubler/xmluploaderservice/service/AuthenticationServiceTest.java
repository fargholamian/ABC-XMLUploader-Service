package com.tradedoubler.xmluploaderservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.tradedoubler.xmluploaderservice.BaseServiceTest;
import com.tradedoubler.xmluploaderservice.configuration.AppConfig;
import com.tradedoubler.xmluploaderservice.model.User;
import com.tradedoubler.xmluploaderservice.enums.Role;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AuthenticationServiceTest extends BaseServiceTest {
  @Mock
  private RestTemplate restTemplate;

  @Mock
  protected AppConfig appConfig;

  @InjectMocks
  private AuthenticationService authenticationService;

  private final String token = "token";

  private final User expectedUser = new User(UUID.randomUUID(), "test-user", "test-password", Role.ROLE_USER);

  @Test
  public void shouldGetUserReturnUserObject_WhenAuthenticationServerReturnUser() {
    setup(expectedUser, HttpStatus.OK);

    Optional<User> user = authenticationService.getUser(token);
    assertThat(user).isPresent();
    assertEquals(user.get(), expectedUser);
  }

  @Test
  public void shouldGetUserReturnEmpty_WhenAuthenticationServerReturnUnAuthorized() {
    setup(null, HttpStatus.UNAUTHORIZED);

    Optional<User> user = authenticationService.getUser(token);
    assertThat(user).isNotPresent();
  }

  private void setup(User expectedUser, HttpStatus httpStatus) {
    String authServerUrl = "auth-server";
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);
    HttpEntity<?> entity = new HttpEntity<HttpHeaders>(null, headers);

    when(restTemplate.exchange(authServerUrl, HttpMethod.GET, entity, User.class))
        .thenReturn(new ResponseEntity<>(expectedUser, httpStatus));

    when(appConfig.getGetUserUri()).thenReturn(authServerUrl);
  }

}
