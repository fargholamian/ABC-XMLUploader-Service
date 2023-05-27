package com.tradedoubler.xmluploaderservice.entity;

import com.tradedoubler.xmluploaderservice.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User  {
  private Long id;
  private String username;
  private String password;
  @Enumerated(EnumType.STRING)
  private Role role;
}