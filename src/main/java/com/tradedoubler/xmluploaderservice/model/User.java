package com.tradedoubler.xmluploaderservice.model;

import com.tradedoubler.xmluploaderservice.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User  {
  private UUID id;
  private String username;
  private String password;
  @Enumerated(EnumType.STRING)
  private Role role;
}