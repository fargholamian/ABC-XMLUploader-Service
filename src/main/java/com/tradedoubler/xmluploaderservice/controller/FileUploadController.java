package com.tradedoubler.xmluploaderservice.controller;

import com.tradedoubler.xmluploaderservice.entity.User;
import com.tradedoubler.xmluploaderservice.util.XmlValidator;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class FileUploadController {

  Logger logger = LoggerFactory.getLogger(FileUploadController.class);

  @Value("${upload.directory}")
  private String uploadDirectory;

  @PostMapping("/upload")
  public ResponseEntity<String> uploadFile(@RequestAttribute User user, @RequestParam("file") MultipartFile file) throws IOException {

    logger.info("The user is: " + user.getUsername());

    if (!XmlValidator.validateXml(file.getInputStream())) {
      return ResponseEntity.badRequest().body("Uploaded xml file is not valid");
    }

    File targetFile = new File(uploadDirectory + File.separator + "temp-" + UUID.randomUUID() + ".xml");
    FileUtils.copyInputStreamToFile(file.getInputStream(), targetFile);

    String result = "File uploaded successfully!";
    return ResponseEntity.ok(result);
  }
}