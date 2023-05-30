package com.tradedoubler.xmluploaderservice.service;

import com.tradedoubler.xmluploaderservice.configuration.AppConfig;
import com.tradedoubler.xmluploaderservice.util.XmlValidator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class XmlService {

  private final AppConfig appConfig;

  public Boolean validate(InputStream inputStream) {
    return XmlValidator.validateXml(inputStream, appConfig.getXsdResource());
  }

  public String store(InputStream inputStream) {
    String filename = UUID.randomUUID() + ".xml";
    File targetFile = new File(appConfig.getUploadDirectory() + File.separator + filename);
    try {
      FileUtils.copyInputStreamToFile(inputStream, targetFile);
      return filename;
    } catch (IOException e) {
      throw new RuntimeException("Something went wrong when storing xml file");
    }
  }
}
