package com.jlog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlog.api.config.AppConfig;
import com.jlog.api.domain.Member;
import com.jlog.api.repository.MemberRepository;
import com.jlog.api.repository.PostRepository;
import com.jlog.api.request.PostCreate;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.jlog.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class PostControllerDocTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AppConfig appConfig;

//    @BeforeEach
//    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//                .apply(documentationConfiguration(restDocumentation))
//                .build();
//    }

//    @Test
//    @DisplayName("글 단건 조회")
//    void test1() throws Exception {
//        // given
//        Post post = Post.builder()
//                .title("123456789012345")
//                .contents("bar")
//                .build();
//
//        postRepository.save(post);
//
//        // expected
//        this.mockMvc.perform(get("/posts/{postId}", 1L).accept(APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andDo(document("post-inquiry",
//                        pathParameters(parameterWithName("postId").description("게시글 ID")
//                        ),
//                        responseFields(
//                                fieldWithPath("id").description("게시글 ID"),
//                                fieldWithPath("title").description("제목"),
//                                fieldWithPath("contents").description("내용")
//                        )
//
//                ));
//    }

    @Test
    @DisplayName("글 등록")
    void test2() throws Exception {
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

        // expected
        this.mockMvc.perform(post("/posts")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", jws)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-create",
                        requestFields(
                                fieldWithPath("title").description("제목").attributes(key("constraint").value("제약조건")),
                                fieldWithPath("contents").description("내용").optional()
                        )
                ));
    }
}
