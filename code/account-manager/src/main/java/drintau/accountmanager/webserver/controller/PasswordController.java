package drintau.accountmanager.webserver.controller;

import drintau.accountmanager.webserver.CommonResult;
import drintau.accountmanager.webserver.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/password")
public class PasswordController {

    private final PasswordService passwordService;

    @PostMapping("/get")
    public CommonResult<String> get() {
        String randomPassword = passwordService.getRandomPassword();
        return new CommonResult<>(randomPassword);
    }

}
