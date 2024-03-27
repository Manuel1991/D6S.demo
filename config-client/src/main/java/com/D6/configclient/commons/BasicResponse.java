package com.D6.configclient.commons;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BasicResponse {
    HttpStatus httpStatus;
    String content;

    public BasicResponse(int httpStatus, String content) {
        this(HttpStatus.valueOf(httpStatus), content);
    }
}