package com.D6.configclient.movies.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MoviesResultDTO {

    @JsonIgnore
    private String links;

    private Integer count;

    private List<MovieDTO> results = new ArrayList<>();
}