package com.jlog.api.service;

import com.jlog.api.crypto.ScryptPasswordEncoder;
import com.jlog.api.domain.Member;
import com.jlog.api.exception.AlreadyExistsEmailException;
import com.jlog.api.exception.InvalidSigninInformation;
import com.jlog.api.repository.MemberRepository;
import com.jlog.api.request.Login;
import com.jlog.api.request.Signup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class AuthServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AuthService authService;

    @AfterEach
    void clean() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void test1() {
        // given
        Signup signup = Signup.builder()
                .password("1234")
                .email("jhseong112@naver.com")
                .nickname("정해성")
                .build();

        // when
        authService.signup(signup);

        // then
        assertEquals(1, memberRepository.count());

        Member member = memberRepository.findAll().iterator().next();
        assertEquals("jhseong112@naver.com", member.getEmail());
        assertNotNull(member.getPassword());
        assertEquals("1234", member.getPassword());
        assertEquals("정해성", member.getNickname());
    }

    @Test
    @DisplayName("회원가입시 중복된 이메일")
    void test2() {
        // given
        Member member = Member.builder()
                .email("jhseong112@naver.com")
                .password("12324")
                .nickname("유재석")
                .build();
        memberRepository.save(member);

        Signup signup = Signup.builder()
                .password("1234")
                .email("jhseong112@naver.com")
                .nickname("정해성")
                .build();

        // expected
        assertThrows(AlreadyExistsEmailException.class, () -> authService.signup(signup));
    }

    @Test
    @DisplayName("로그인 성공")
    void test3() {
        ScryptPasswordEncoder encoder = new ScryptPasswordEncoder();
        String encrypt = encoder.encrypt("1234");

        // given
        Member member = Member.builder()
                .email("jhseong112@naver.com")
                .password("1234")
                .nickname("유재석")
                .build();
        memberRepository.save(member);

        Login login = Login.builder()
                .email("jhseong112@naver.com")
                .password("1234")
                .build();

        // when
        Member findMember = authService.signin(login);

        // then
        assertNotNull(findMember);
    }

    @Test
    @DisplayName("로그인 비밀번호 틀림")
    void test4() {
        // given
        ScryptPasswordEncoder encoder = new ScryptPasswordEncoder();
        String encrypt = encoder.encrypt("1234");

        // given
        Member member = Member.builder()
                .email("jhseong112@naver.com")
                .password(encrypt)
                .nickname("유재석")
                .build();
        memberRepository.save(member);

        Login login = Login.builder()
                .email("jhseong112@naver.com")
                .password("5678")
                .build();

        // expected
        assertThrows(InvalidSigninInformation.class, () -> authService.signin(login));
    }

}