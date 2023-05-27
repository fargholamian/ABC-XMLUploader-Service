package com.tradedoubler.xmluploaderservice.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {

  private final KafkaTemplate<String, String> kafkaTemplate;

  public EventProducer(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendEvent(String event) {
    kafkaTemplate.send("topic-name", event);
  }
}