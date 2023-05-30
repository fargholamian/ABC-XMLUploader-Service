package com.tradedoubler.xmluploaderservice.configuration;

import com.tradedoubler.xmluploaderservice.model.User;
import com.tradedoubler.xmluploaderservice.enums.Role;
import com.tradedoubler.xmluploaderservice.service.AuthenticationService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@AllArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

  private AuthenticationService authenticationService;
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String token = getTokenFromRequest(request);
    if (StringUtils.isBlank(token)) {
      response.sendError(401, "Invalid token");
    }

    Optional<User> user = authenticationService.getUser(token);
    if (user.isPresent() && Role.ROLE_USER.equals(user.get().getRole())) {
      request.setAttribute("user", user.get());
      return true;
    }

    response.sendError(401, "Unauthorized");
    return false;
  }

  public static String getTokenFromRequest(HttpServletRequest httpServletRequest) {
    String requestTokenHeader = httpServletRequest.getHeader("Authorization");
    return requestTokenHeader != null && requestTokenHeader
        .startsWith("Bearer")
        ? requestTokenHeader.substring("Bearer".length())
        : null;
  }

}