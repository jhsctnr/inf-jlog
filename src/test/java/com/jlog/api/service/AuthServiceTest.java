package com.jlog.api.service;

import com.jlog.api.domain.Member;
import com.jlog.api.exception.AlreadyExistsEmailException;
import com.jlog.api.repository.MemberRepository;
import com.jlog.api.request.Signup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
                .name("정해성")
                .build();

        // when
        authService.signup(signup);

        // then
        assertEquals(1, memberRepository.count());

        Member member = memberRepository.findAll().iterator().next();
        assertEquals("jhseong112@naver.com", member.getEmail());
        assertEquals("1234", member.getPassword());
        assertEquals("정해성", member.getName());
    }

    @Test
    @DisplayName("회원가입시 중복된 이메일")
    void test2() {
        // given
        Member member = Member.builder()
                .email("jhseong112@naver.com")
                .password("12324")
                .name("유재석")
                .build();
        memberRepository.save(member);

        Signup signup = Signup.builder()
                .password("1234")
                .email("jhseong112@naver.com")
                .name("정해성")
                .build();

        // expected
        assertThrows(AlreadyExistsEmailException.class, () -> authService.signup(signup));
    }

}