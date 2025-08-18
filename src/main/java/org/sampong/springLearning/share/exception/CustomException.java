package org.sampong.springLearning.share.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    HttpStatus staus;
    String message;
    public CustomException(HttpStatus status, String message) {
        super(message);
    }
}
