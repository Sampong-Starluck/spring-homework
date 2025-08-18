package org.sampong.springLearning.share.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Builder
public class Response {
    private Integer status;
    private String message;

    public static Response success() {
        return new Response(200, "Success");
    }

    public static Response error() {
        return new Response(404, "Error");
    }
}