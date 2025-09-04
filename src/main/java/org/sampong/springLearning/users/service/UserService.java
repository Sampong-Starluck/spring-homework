package org.sampong.springLearning.users.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.sampong.springLearning.users.controller.dto.request.CreateUserRequest;
import org.sampong.springLearning.users.controller.dto.request.JwtRequest;
import org.sampong.springLearning.users.controller.dto.response.JwtResponse;
import org.sampong.springLearning.users.model.Users;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String username);
    Users createUser(CreateUserRequest user);
    JwtResponse login(JwtRequest req) throws JsonProcessingException;
    JwtResponse register(CreateUserRequest req) throws JsonProcessingException;
}
