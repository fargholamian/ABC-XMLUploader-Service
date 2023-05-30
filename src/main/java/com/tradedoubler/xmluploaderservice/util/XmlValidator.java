package com.tradedoubler.xmluploaderservice.util;

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
import org.springframework.core.io.Resource;

public class XmlValidator {
  private final static Logger logger = LoggerFactory.getLogger(XmlValidator.class);

  public static Boolean validateXml(InputStream inputStream, Resource xsdFileResource) {
    try {
      XMLInputFactory inputFactory = XMLInputFactory.newInstance();
      XMLStreamReader reader = inputFactory.createXMLStreamReader(inputStream);

      SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

      Schema schema = schemaFactory.newSchema(new StreamSource(xsdFileResource.getFile()));

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