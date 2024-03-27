package com.D6.configclient.movies;

import com.D6.configclient.movies.dtos.ApiErrorDTO;
import com.D6.configclient.movies.dtos.MoviesResultDTO;
import com.D6.configclient.exceptions.ApiException;
import com.D6.configclient.commons.BasicResponse;
import com.D6.configclient.commons.HttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MoviesService {

    @Autowired
    private HttpClient httpClient;

    @Value("${apis.movies.url}")
    private String moviesUrl;

    @Value("${apis.movies.headers}")
    private String moviesHeaders;

    private String apiKey = "b2d76e1138msh04892c3280f71c7p1ecf6djsncee67a4b5d4d";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public MoviesService() {
        moviesHeaders = String.format("X-RapidAPI-Key:%s;", apiKey).concat(Optional.ofNullable(moviesHeaders).orElse(""));
    }

    private boolean responseIsOk(BasicResponse response) {
        return Optional
                .of(response)
                .map(BasicResponse::getHttpStatus)
                .filter(HttpStatus::is2xxSuccessful)
                .isPresent();
    }

    public MoviesResultDTO getMoviesByYear(Year year) {

        URI uri = URI.create(String.format("%s/movie/byYear/%s", moviesUrl, year));

        Map<String, String> headers = Stream.ofNullable(moviesHeaders)
                .map(s -> s.split(";"))
                .map(List::of)
                .flatMap(Collection::stream)
                .filter(Strings::isNotBlank)
                .map(h -> h.split(":"))
                .filter(a -> a.length == 2)
                .collect(Collectors.toMap(pair -> pair[0], pair -> pair[1]));

        try {
            BasicResponse response = httpClient.get(uri, headers);
            if (responseIsOk(response)) {
                return objectMapper.readValue(response.getContent(), MoviesResultDTO.class);
            } else {
                ApiErrorDTO errorDTO = objectMapper.readValue(response.getContent(), ApiErrorDTO.class);
                throw new ApiException(
                        response.getHttpStatus(),
                        String.format("%s. %s", errorDTO.getMessages(), errorDTO.getInfo())
                );
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
