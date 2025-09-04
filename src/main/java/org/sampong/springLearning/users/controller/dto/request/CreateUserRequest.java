package org.sampong.springLearning.users.controller.dto.request;

import lombok.Data;
import org.sampong.springLearning.share.enumerate.RoleStatus;

import java.util.List;

@Data
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private List<RoleStatus> roles;
}
