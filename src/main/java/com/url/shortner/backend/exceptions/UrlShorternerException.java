package com.url.shortner.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UrlShorternerException {

    @ExceptionHandler(value = InternalServerException.class)
    public ResponseEntity<Object> exception(InternalServerException exception) {
        return new ResponseEntity<>("OOPS, something went wrong!", HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(value = InvalidUrlException.class)
    public ResponseEntity<Object> exception(InvalidUrlException exception) {
        return new ResponseEntity<>("You have entered an invalid url", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NoDataFoundException.class)
    public ResponseEntity<Object> exception(NoDataFoundException exception) {
        return new ResponseEntity<>("No shortened data exist for this url", HttpStatus.NOT_FOUND);
    }
}
