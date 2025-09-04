package org.sampong.springLearning.users.controller.dto.request;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}
