package com.D6.configclient.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Objects;

@Component
public class XmlValidator {

    private final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    private final Logger logger = LoggerFactory.getLogger(XmlValidator.class);

    private File getFile(String location) {
        return new File(Objects.requireNonNull(getClass().getClassLoader().getResource(location)).getFile());
    }

    private Validator initValidator(String xsdPath) throws SAXException {
        Source schemaFile = new StreamSource(getFile(xsdPath));
        Schema schema = schemaFactory.newSchema(schemaFile);
        return schema.newValidator();
    }

    public boolean isValid(String xsdPathResource, String xml) {

        XmlErrorHandler xmlErrorHandler = new XmlErrorHandler();

        try {

            Validator validator = initValidator(xsdPathResource);
            validator.setErrorHandler(xmlErrorHandler);
            validator.validate(new StreamSource(new StringReader(xml)));

        } catch (IOException | SAXException e) {
            logger.error(e.getMessage());
            return false;
        }

        if (xmlErrorHandler.hasExceptions()) {
            xmlErrorHandler.getExceptions().forEach(e -> {
                logger.info(String.format("Line number: %s, Column number: %s. %s", e.getLineNumber(), e.getColumnNumber(), e.getMessage()));
            });
            return false;
        }

        return true;
    }
}
