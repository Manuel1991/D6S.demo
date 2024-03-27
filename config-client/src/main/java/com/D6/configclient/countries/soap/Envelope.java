package com.D6.configclient.countries.soap;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "soap:Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
public class Envelope {

    //@JacksonXmlProperty(isAttribute = true)
    //private String soap;

    //@JacksonXmlProperty(isAttribute = true)
    //private String xmlns;

    @JacksonXmlProperty(localName = "Body", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
    private Body body;
}
