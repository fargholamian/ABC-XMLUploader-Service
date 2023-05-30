package com.tradedoubler.xmluploaderservice.controller;

import com.tradedoubler.xmluploaderservice.model.Event;
import com.tradedoubler.xmluploaderservice.model.User;
import com.tradedoubler.xmluploaderservice.service.EventProducer;
import com.tradedoubler.xmluploaderservice.service.XmlService;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FileUploadController {

  Logger logger = LoggerFactory.getLogger(FileUploadController.class);

  private final EventProducer eventProducer;

  private final XmlService xmlService;

  @PostMapping("/upload")
  public ResponseEntity<String> uploadFile(@RequestAttribute User user, @RequestParam("file") MultipartFile file) throws IOException {

    if (!xmlService.validate(file.getInputStream())) {
      throw new RuntimeException("Uploaded xml file is not valid");
    }

    String filename = xmlService.store(file.getInputStream());

    eventProducer.sendEvent(new Event(UUID.randomUUID(), user.getId(), filename));

    String result = "File uploaded successfully!";
    return ResponseEntity.ok(result);
  }
}