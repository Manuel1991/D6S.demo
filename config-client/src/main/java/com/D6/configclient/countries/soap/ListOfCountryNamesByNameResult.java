package com.D6.configclient.countries.soap;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListOfCountryNamesByNameResult {

    //@JacksonXmlProperty(isAttribute = true)
    //private String m;

    @JacksonXmlProperty(localName = "tCountryCodeAndName", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<CountryCodeAndName> countries;
}
