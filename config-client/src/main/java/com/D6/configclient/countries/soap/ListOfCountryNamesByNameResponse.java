package com.D6.configclient.countries.soap;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListOfCountryNamesByNameResponse {

    //@JacksonXmlProperty(isAttribute = true)
    //private String soap;

    //@JacksonXmlProperty(isAttribute = true)
    //private String xmlns;

    //@JacksonXmlProperty(isAttribute = true)
    //private String m;

    @JacksonXmlProperty(localName = "ListOfCountryNamesByNameResult", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    private ListOfCountryNamesByNameResult result;
}
