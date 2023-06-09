package com.jlog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlog.api.config.AppConfig;
import com.jlog.api.domain.Member;
import com.jlog.api.domain.Post;
import com.jlog.api.repository.MemberRepository;
import com.jlog.api.repository.PostRepository;
import com.jlog.api.request.PostCreate;
import com.jlog.api.request.PostEdit;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AppConfig appConfig;

    @AfterEach
    void clean() {
        postRepository.deleteAll();
    }


    @Test
    @DisplayName("글 작성 요청시 title 값은 필수다.")
    void test2() throws Exception {
        // given
        PostCreate request = PostCreate.builder()
                .contents("내용입니다.")
                .build();
        String json = objectMapper.writeValueAsString(request);

        Member member = Member.builder()
                .email("jhseong112@naver.com")
                .password("12324")
                .nickname("유재석")
                .build();
        memberRepository.save(member);

        SecretKey key = Keys.hmacShaKeyFor(appConfig.getJwtKey());

        String jws = Jwts.builder()
                .setSubject(String.valueOf(member.getId()))
                .signWith(key)
                .setIssuedAt(new Date())
                .compact();

        // expected
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                                .header("Authorization", jws)
                // {"title": ""}
                // {"title": null}
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("글 작성 요청시 DB에 값이 저장된다.")
    void test3() throws Exception {
        // given
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .contents("내용입니다.")
                .build();
        String json = objectMapper.writeValueAsString(request);

        Member member = Member.builder()
                .email("jhseong112@naver.com")
                .password("12324")
                .nickname("유재석")
                .build();
        memberRepository.save(member);

        SecretKey key = Keys.hmacShaKeyFor(appConfig.getJwtKey());

        String jws = Jwts.builder()
                .setSubject(String.valueOf(member.getId()))
                .signWith(key)
                .setIssuedAt(new Date())
                .compact();

        // when
        mockMvc.perform(post("/posts")
                        .header("Authorization", jws)
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());
        // then
        assertThat(postRepository.count()).isEqualTo(1L);

        Post findPost = postRepository.findAll().get(0);
        assertThat(findPost.getTitle()).isEqualTo("제목입니다.");
        assertThat(findPost.getContents()).isEqualTo("내용입니다.");
    }

    @Test
    @DisplayName("글 1개 조회")
    void test4() throws Exception {
        // given
        Post post = Post.builder()
                .title("123456789012345")
                .contents("bar")
                .build();

        postRepository.save(post);

        // expected
        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("123456789012345"))
                .andExpect(jsonPath("$.contents").value("bar"))
                .andDo(print());
    }

    @Test
    @DisplayName("페이지를 0으로 요청하면 첫 페이지를 가져온다.")
    void test5() throws Exception {
        // given
        List<Post> requestPosts = IntStream.range(1, 31)
                .mapToObj(i -> Post.builder()
                        .title("포스트 제목 " + i)
                        .contents("반포자이 " + i)
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        // expected ///posts?page=1&sort=id,desc
        mockMvc.perform(get("/posts?")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                /**
                 * 단 건
                 * {id: ..., title: ...}
                 */

                /**
                 * 여러 건
                 * [{id: ..., title:...}, {id: ..., title: ...}]
                 */
                .andExpect(jsonPath("$.length()", Matchers.is(10)))
                .andExpect(jsonPath("$[0].id").value(requestPosts.get(requestPosts.size() - 1).getId()))
                .andExpect(jsonPath("$[0].title").value("포스트 제목 30"))
                .andExpect(jsonPath("$[0].contents").value("반포자이 30"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 제목 수정")
    void test6() throws Exception {
        // given
        Post post = Post.builder()
                .title("해성맨")
                .contents("반포자이")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("해성걸")
                .contents("반포자이")
                .build();

        // expected
        mockMvc.perform(patch("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 삭제")
    void test7() throws Exception {
        // given
        Post post = Post.builder()
                .title("해성맨")
                .contents("반포자이")
                .build();
        postRepository.save(post);

        // expected
        mockMvc.perform(delete("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 게시글 조회")
    void test9() throws Exception {
        // expected
        mockMvc.perform(delete("/posts/{postId}", 1L)
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 게시글 수정")
    void test10() throws Exception {
        // given
        PostEdit postEdit = PostEdit.builder()
                .title("해성")
                .contents("반포자이")
                .build();

        // expected
        mockMvc.perform(patch("/posts/{postId}", 100L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit))
                )
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 작성 시 제목에 '바보'는 포함될 수 없다.")
    void test11() throws Exception {
        // given
        PostCreate request = PostCreate.builder()
                .title("나는 바보입니다.")
                .contents("내용입니다.")
                .build();
        String json = objectMapper.writeValueAsString(request);

        Member member = Member.builder()
                .email("jhseong112@naver.com")
                .password("12324")
                .nickname("유재석")
                .build();
        memberRepository.save(member);

        SecretKey key = Keys.hmacShaKeyFor(appConfig.getJwtKey());

        String jws = Jwts.builder()
                .setSubject(String.valueOf(member.getId()))
                .signWith(key)
                .setIssuedAt(new Date())
                .compact();

        // when
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                                .header("Authorization", jws)
                        // {"title": ""}
                        // {"title": null}
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
        // then
    }
}