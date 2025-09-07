package org.sampong.springLearning.users.controller;

import lombok.RequiredArgsConstructor;
import org.sampong.springLearning.share.base.BaseResponse;
import org.sampong.springLearning.share.constant.AppConstants;
import org.sampong.springLearning.users.controller.mapper.UserRestMapper;
import org.sampong.springLearning.users.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppConstants.WEB_PATH+"/user")
@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
public class UserController {
    private final UserService userService;
    private final BaseResponse response;
    private final UserRestMapper mapper;

    @GetMapping("/{id}")
    public Map<String, Object> findById(@PathVariable Long id) {
        return Optional.of(userService.findById(id))
                .map(mapper::from)
                .map(response::response).orElse(null);
    }

    @GetMapping("/get-info")
    public Map<String, Object> getUserInfo() {
        return Optional.of(userService.getUserInfo())
                .map(mapper::from)
                .map(response::response).orElse(null);
    }
}
