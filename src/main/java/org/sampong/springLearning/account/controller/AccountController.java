package org.sampong.springLearning.account.controller;

import lombok.RequiredArgsConstructor;
import org.sampong.springLearning.account.model.Account;
import org.sampong.springLearning.account.service.AccountService;
import org.sampong.springLearning.share.base.BaseResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/web/account")
public class AccountController {
    private final AccountService service;
    private final BaseResponse response;

    @GetMapping("/{id}")
    public Map<String, Object> getById (@PathVariable("id") Long id) {
        return response.response(service.findById(id));
    }

    @PostMapping
    public Map<String, Object> addNewAccount (@RequestBody Account account) {
        return response.response(service.addNew(account));
    }

    @PutMapping
    public Map<String, Object> updateAccount (@RequestBody Account account) {
        return response.response(service.updateObj(account));
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> deleteAccount (@PathVariable("id") Long id) {
        service.delete(id);
        return response.responseCode(200, "Account deleted successfully");
    }
}
