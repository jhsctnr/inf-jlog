package com.jlog.api.controller;

import com.jlog.api.config.AppConfig;
import com.jlog.api.domain.Member;
import com.jlog.api.repository.MemberRepository;
import com.jlog.api.request.Login;
import com.jlog.api.request.Signup;
import com.jlog.api.response.SessionResponse;
import com.jlog.api.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MemberRepository memberRepository;
    private final AppConfig appConfig;

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login) {
//        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        byte[] encodedKey = key.getEncoded();
//        String strKey = Base64.getEncoder().encodeToString(encodedKey);

        Member member = authService.signin(login);


        SecretKey key = Keys.hmacShaKeyFor(appConfig.getJwtKey());

        String jws = Jwts.builder()
                .setSubject(String.valueOf(member.getId()))
                .signWith(key)
                .setIssuedAt(new Date())
                .compact();

        return new SessionResponse(jws, member.getNickname());
    }

//    @PostMapping("/auth/login")
//    public ResponseEntity<Object> login(@RequestBody Login login) {
//        String accessToken = authService.signin(login);
//        ResponseCookie cookie = ResponseCookie.from("SESSION", accessToken)
//                .domain("localhost") // todo 서버 환경에 따른 분리 필요
//                .path("/")
//                .httpOnly(true)
//                .secure(false)
//                .maxAge(Duration.ofDays(30))
//                .sameSite("Strict")
//                .build();
//        log.info(">>>>>>>>> cookie={}", cookie.toString());
//        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
//    }

    @PostMapping("/auth/signup")
    public void singup(@RequestBody Signup signup) {
        authService.signup(signup);
    }

//    @PostMapping("/auth/login")
//    public SessionResponse login(@RequestBody Login login) {
//        String accessToken = authService.signin(login);
//        return new SessionResponse(accessToken);
//    }
}
