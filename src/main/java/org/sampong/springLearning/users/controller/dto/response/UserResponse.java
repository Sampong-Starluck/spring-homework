package org.sampong.springLearning.users.controller.dto.response;

import org.sampong.springLearning.share.enumerate.RoleStatus;

import java.util.List;

public record UserResponse (
        Long id,
        String username,
        String phoneNumber,
        String familyName,
        String givenName,
        String email,
        List<RoleStatus> roleStatus
) {

}
