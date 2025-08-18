package org.sampong.springLearning.share.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handle(CustomException e) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> response = new HashMap<>();
        map.put("message", e.getMessage());
        map.put("status", e.staus);
        response.put("data", map);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception e) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> response = new HashMap<>();
        map.put("message", "Error happened, Something went wrong");
        map.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        response.put("data", map);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
