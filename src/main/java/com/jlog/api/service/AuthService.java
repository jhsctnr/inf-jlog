package com.jlog.api.service;

import com.jlog.api.crypto.PasswordEncoder;
import com.jlog.api.domain.Member;
import com.jlog.api.domain.Session;
import com.jlog.api.exception.AlreadyExistsEmailException;
import com.jlog.api.exception.InvalidSigninInformation;
import com.jlog.api.repository.MemberRepository;
import com.jlog.api.request.Login;
import com.jlog.api.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long signin(Login login) {

        // DB에서 조회
        Member member = memberRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSigninInformation::new);

//        Member member = memberRepository.findByEmail(login.getEmail())
//                .orElseThrow(InvalidSigninInformation::new);


//        boolean matches = passwordEncoder.matches(login.getPassword(), member.getPassword());
//        if (!matches) {
//            throw new InvalidSigninInformation();
//        }

        // 토근을 응답
        Session session = member.addSession();
        return member.getId();
    }

    public void signup(Signup signup) {
        Optional<Member> optionalMember = memberRepository.findByEmail(signup.getEmail());
        if (optionalMember.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        String encryptedPassword = passwordEncoder.encrypt(signup.getPassword());

        Member member = Member.builder()
                .name(signup.getName())
                .password(encryptedPassword)
                .email(signup.getEmail())
                .build();
        memberRepository.save(member);
    }
}
