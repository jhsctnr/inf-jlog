package com.jlog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlog.api.config.AppConfig;
import com.jlog.api.domain.Member;
import com.jlog.api.domain.Session;
import com.jlog.api.repository.MemberRepository;
import com.jlog.api.repository.SessionRepository;
import com.jlog.api.request.Login;
import com.jlog.api.request.Signup;
import com.jlog.api.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    AuthService authService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    AppConfig appConfig;

    @BeforeEach
    void clean() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 요청 성공")
    void test() throws Exception {
        // given
        memberRepository.save(Member.builder()
                .nickname("해성")
                .email("jhseong112@naver.com")
                .password("1234")
                .build());

        Login login = Login.builder()
                .email("jhseong112@naver.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        // expected
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 요청 성공후 세션 1개 생성")
    void test2() throws Exception {
        // given
        Member member = memberRepository.save(Member.builder()
                .nickname("해성")
                .email("jhseong112@naver.com")
                .password("1234")
                .build());

        Login login = Login.builder()
                .email("jhseong112@naver.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        // expected
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        List<Session> sessions = sessionRepository.findByMemberId(member.getId());
        assertThat(sessions.size()).isEqualTo(1L);
    }

    @Test
    @DisplayName("로그인 요청 성공후 세션 응답")
    void test3() throws Exception {
        // given
        Member member = memberRepository.save(Member.builder()
                .nickname("해성")
                .email("jhseong112@naver.com")
                .password("1234")
                .build());

        Login login = Login.builder()
                .email("jhseong112@naver.com")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(login);

        // expected
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].value", notNullValue()))
//                .andExpect(cookie().("value", is(notNullValue())))
                .andDo(print());

    }

    @Test
    @DisplayName("로그인 후 권한이 필요한 페이지에 접속한다 /foo")
    void test4() throws Exception {
        // given
        Member member = memberRepository.save(Member.builder()
                .nickname("해성")
                .email("jhseong112@naver.com")
                .password("1234")
                .build());
        Session session = member.addSession();
        memberRepository.save(member);

        Login login = Login.builder()
                .email("jhseong112@naver.com")
                .password("1234")
                .build();

        Member findMember = authService.signin(login);

        SecretKey key = Keys.hmacShaKeyFor(appConfig.getJwtKey());

        String jws = Jwts.builder()
                .setSubject(String.valueOf(findMember.getId()))
                .signWith(key)
                .setIssuedAt(new Date())
                .compact();

        // expected
        mockMvc.perform(get("/foo")
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", jws)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 후 검증되지 않은 세션 값으로 권한이 필요한 페이지에 접속할 수 없다.")
    void test5() throws Exception {
        // given
        Member member = memberRepository.save(Member.builder()
                .nickname("해성")
                .email("jhseong112@naver.com")
                .password("1234")
                .build());
        Session session = member.addSession();
        memberRepository.save(member);

        // expected
        mockMvc.perform(get("/foo")
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", session.getAccessToken() + "-other")
                )
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입")
    void test6() throws Exception {
        // given
        Signup signup = Signup.builder()
                .password("1234")
                .email("jhseong112@naver.com")
                .nickname("정해성")
                .build();

        // expected
        mockMvc.perform(post("/auth/signup")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signup))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 요청 에러")
    void test0() throws Exception {
        // given
        Map<String, String> request = new HashMap<>();
        request.put("email", "jhseong112@naver.com");
        request.put("password", "5555");
        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("아이디/비밀번호가 올바르지 않습니다."))
                .andDo(print());
    }

}