package com.tradedoubler.xmluploaderservice.util;

import com.tradedoubler.xmluploaderservice.controller.FileUploadController;
import java.io.File;
import java.io.InputStream;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.transform.stax.StAXSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlValidator {
  private final static Logger logger = LoggerFactory.getLogger(XmlValidator.class);

  public static Boolean validateXml(InputStream inputStream) {
    try {
      XMLInputFactory inputFactory = XMLInputFactory.newInstance();
      XMLStreamReader reader = inputFactory.createXMLStreamReader(inputStream);

      SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

      Schema schema = schemaFactory.newSchema(new StreamSource(new File(
          FileUploadController.class.getClassLoader().getResource("files/Sample0.xsd").getFile())));

      Validator validator = schema.newValidator();

      validator.validate(new StAXSource(reader));

      logger.info("XML is valid.");
      return Boolean.TRUE;
    } catch (Exception e) {
      logger.info("XML is invalid.");
      return Boolean.FALSE;
    }
  }
}