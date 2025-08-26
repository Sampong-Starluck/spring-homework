package org.sampong.springLearning.share.exception;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatusCode status,
            @NotNull WebRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            FieldError fieldError = (FieldError) error;
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        map.put("status", status.value());
        map.put("error", "VALIDATION FAILED");
        map.put("message", "Error validation");
        response.put("response", map);
        response.put("fields", errors);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @ExceptionHandler(CustomException.class)
    ResponseEntity<Object> handleCustomException(CustomException ex) {
        Map<String, Object> response = new HashMap<>();
        var map = new HashMap<String, Object>();
        map.put("status", ex.getStatus());
        map.put("message", ex.getMessage());
        map.put("error", ex.getError());
        response.put("response", map);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception e) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> response = new HashMap<>();
        logger.error(e.getMessage(), e);
        map.put("message", "Error happened, Something went wrong");
        map.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        response.put("data", map);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
