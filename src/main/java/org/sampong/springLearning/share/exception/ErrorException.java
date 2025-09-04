package org.sampong.springLearning.share.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorException {

    // Getters and Setters
    private Integer status;
    private String error;
    private String message;

    public ErrorException() {
        this.status = HttpStatus.UNAUTHORIZED.value();
        this.error = HttpStatus.UNAUTHORIZED.name();
    }

    public ErrorException(Integer status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }

}
