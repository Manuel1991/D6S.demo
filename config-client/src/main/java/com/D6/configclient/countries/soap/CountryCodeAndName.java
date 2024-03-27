package com.D6.configclient.countries.soap;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
public class CountryCodeAndName {

    //@JacksonXmlProperty(isAttribute = true)
    //private String m;

    @JacksonXmlProperty(localName = "sISOCode", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    private String isoCode;

    @JacksonXmlProperty(localName = "sName", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    private String name;

    public CountryCodeAndName(String isoCode) {
        this(isoCode, null);
    }

    public CountryCodeAndName(String isoCode, String name) {
        this.isoCode = isoCode;
        this.name = name;
    }
}