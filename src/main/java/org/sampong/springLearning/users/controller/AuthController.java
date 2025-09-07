package org.sampong.springLearning.users.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.sampong.springLearning.share.base.BaseResponse;
import org.sampong.springLearning.share.constant.AppConstants;
import org.sampong.springLearning.share.exception.CustomException;
import org.sampong.springLearning.users.controller.dto.request.CreateUserRequest;
import org.sampong.springLearning.users.controller.dto.request.JwtRequest;
import org.sampong.springLearning.users.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.WEB_PATH +"/auth")
public class AuthController {
    private final UserService service;
    private final BaseResponse response;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    Map<String, Object> register(@RequestBody CreateUserRequest request) throws UsernameNotFoundException, JsonProcessingException {
        return response.response(service.register(request));
    }

    @PostMapping("/login")
    Map<String, Object> login(@RequestBody JwtRequest request) throws JsonProcessingException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (AuthenticationException e) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, e.getLocalizedMessage());
        }
        return response.response(service.login(request));
    }
}
