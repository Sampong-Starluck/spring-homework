package org.sampong.springLearning.share.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class CustomException extends RuntimeException {

    private final int status;
    private final String error;

    public CustomException(HttpStatus status, String message) {
        super(message);
        this.status = status.value();
        this.error = status.getReasonPhrase();
    }

}