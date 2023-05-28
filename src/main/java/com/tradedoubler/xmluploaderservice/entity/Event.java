package com.tradedoubler.xmluploaderservice.entity;

import java.util.UUID;
import lombok.Data;

@Data
public class Event {
  private final UUID id;
  private final UUID userId;
  private final String filename;
}
