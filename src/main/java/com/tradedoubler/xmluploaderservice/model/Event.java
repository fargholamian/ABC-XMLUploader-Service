package com.tradedoubler.xmluploaderservice.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Event {
  private final UUID id;
  private final UUID userId;
  private final String filename;
}
