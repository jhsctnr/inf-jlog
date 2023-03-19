package com.jlog.api.controller;

import com.jlog.api.domain.Member;
import com.jlog.api.exception.InvalidSigninInformation;
import com.jlog.api.repository.MemberRepository;
import com.jlog.api.request.Login;
import com.jlog.api.response.SessionResponse;
import com.jlog.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login) {
        // json 아이디/비밀번호
        log.info(">>>login={}", login.toString());
        String accessToken = authService.signin(login);
        return new SessionResponse(accessToken);
    }
}
