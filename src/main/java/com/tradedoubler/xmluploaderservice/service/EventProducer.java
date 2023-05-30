package com.tradedoubler.xmluploaderservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradedoubler.xmluploaderservice.model.Event;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {

  private final KafkaTemplate<String, String> kafkaTemplate;

  public EventProducer(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendEvent(Event event) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    kafkaTemplate.send("file-received-event", mapper.writeValueAsString(event));
  }
}