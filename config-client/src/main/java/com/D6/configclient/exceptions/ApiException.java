package com.D6.configclient.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ApiException extends RuntimeException {

    private HttpStatus httpStatus;

    public ApiException(HttpStatus httpStatus, String message) {
        super(message);
        setHttpStatus(httpStatus);
    }

    public ApiException(int httpStatus, String message) {
        super(message);
        setHttpStatus(HttpStatus.valueOf(httpStatus));
    }
}
