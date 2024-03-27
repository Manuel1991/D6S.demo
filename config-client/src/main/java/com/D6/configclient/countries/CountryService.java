package com.D6.configclient.countries;

import com.D6.configclient.commons.BasicResponse;
import com.D6.configclient.commons.HttpClient;
import com.D6.configclient.commons.XmlValidator;
import com.D6.configclient.countries.dtos.CountryDTO;
import com.D6.configclient.countries.soap.Body;
import com.D6.configclient.countries.soap.Envelope;
import com.D6.configclient.countries.soap.ListOfCountryNamesByNameResponse;
import com.D6.configclient.countries.soap.ListOfCountryNamesByNameResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CountryService {

    @Value("${apis.countries.url}")
    private String countriesUrl;

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private XmlValidator xmlValidator;

    private final ObjectMapper objectMapper = new ObjectMapper();


    public Envelope getRawCountries() throws JsonProcessingException {
        BasicResponse response = httpClient.post(
                URI.create(countriesUrl),
                Map.of("Content-Type", "text/xml; charset=utf-8"),
                "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap12:Envelope xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\"><soap12:Body><ListOfCountryNamesByName xmlns=\"http://www.oorsprong.org/websamples.countryinfo\"></ListOfCountryNamesByName></soap12:Body></soap12:Envelope>"
        );

        String content = response.getContent();

        boolean isValid = xmlValidator.isValid("xsd/country.xsd", content);

        if (!isValid)
            return null;

        XmlMapper xmlMapper = new XmlMapper();

        return xmlMapper.readValue(content, Envelope.class);
    }

    public List<CountryDTO> getCountries() throws ParserConfigurationException, IOException, SAXException {

        Envelope envelope = getRawCountries();

        return Optional.ofNullable(envelope)
                .map(Envelope::getBody)
                .map(Body::getResponse)
                .map(ListOfCountryNamesByNameResponse::getResult)
                .map(ListOfCountryNamesByNameResult::getCountries)
                .stream()
                .flatMap(Collection::stream)
                .map(c -> new CountryDTO(c.getIsoCode(), c.getName()))
                .collect(Collectors.toList());


    }
}
