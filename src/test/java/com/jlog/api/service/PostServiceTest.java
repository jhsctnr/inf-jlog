package com.jlog.api.service;

import com.jlog.api.domain.Post;
import com.jlog.api.exception.PostNotFound;
import com.jlog.api.repository.PostRepository;
import com.jlog.api.request.PostCreate;
import com.jlog.api.request.PostEdit;
import com.jlog.api.request.PostSearch;
import com.jlog.api.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        // when
        postService.write(postCreate);

        // then
        assertThat(postRepository.count()).isEqualTo(1L);
        Post findPost = postRepository.findAll().get(0);
        assertThat(findPost.getTitle()).isEqualTo("제목입니다.");
        assertThat(findPost.getContent()).isEqualTo("내용입니다.");
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() {
        // given
        Post post = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(post);

        // 클라이언트 요구사항
            // json응답에서 title값 길이를 최대 10글자로 해주세요.
            // 사실 이런 요청은 클라이언트에서 하는 게 좋다.
        // 응답 클래스 분리하세요 (서비스 정책에 맞는)


        // when
        PostResponse response = postService.get(post.getId());

        //then
        assertThat(response).isNotNull();
        assertThat(postRepository.count()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("foo");
        assertThat(response.getContent()).isEqualTo("bar");
    }

    @Test
    @DisplayName("글 여러개 조회")
    void test3() {
        // given
        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("foo" + i)
                        .content("bar" + i)
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        // sql -> select, limit, offset

        // when
//        Pageable pageable = PageRequest.of(0, 5, DESC, "id");
        PostSearch postSearch = PostSearch.builder()
                .page(1L)
                .build();


        List<PostResponse> posts = postService.getList(postSearch);

        //then
        assertThat(posts.size()).isEqualTo(10);
        assertThat(posts.get(0).getTitle()).isEqualTo("foo19");
    }

    @Test
    @DisplayName("글 제목 수정")
    void test4() {
        // given
        Post post = Post.builder()
                .title("해성맨")
                .content("반포자이")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("해성걸")
                .content("반포자이")
                .build();
        // when
        postService.edit(post.getId(), postEdit);

        //then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));
        assertThat(changedPost.getTitle()).isEqualTo("해성걸");
        assertThat(changedPost.getContent()).isEqualTo("반포자이");
    }

    @Test
    @DisplayName("글 내용 수정")
    void test5() {
        // given
        Post post = Post.builder()
                .title("해성맨")
                .content("반포자이")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("해성맨")
                .content("초가집")
                .build();
        // when
        postService.edit(post.getId(), postEdit);

        //then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id=" + post.getId()));
        assertThat(changedPost.getTitle()).isEqualTo("해성맨");
        assertThat(changedPost.getContent()).isEqualTo("초가집");
    }

    @Test
    @DisplayName("게시글 삭제")
    void test6() {
        // given
        Post post = Post.builder()
                .title("해성맨")
                .content("반포자이")
                .build();
        postRepository.save(post);

        // when
        postService.delete(post.getId());

        //then
        assertThat(postRepository.count()).isEqualTo(0);
    }

    @Test
    @DisplayName("글 1개 조회 - 존재하지 않는 글")
    void test7() {
        // given
        Post post = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(post);

        // expected
        assertThatThrownBy(() -> postService.get(post.getId() + 1L)).isInstanceOf(PostNotFound.class);
//        PostNotFound e = assertThrows(PostNotFound.class, () -> postService.get(post.getId() + 1L));

    }

    @Test
    @DisplayName("게시글 삭제 - 존재하지 않는 글")
    void test8() {
        // given
        Post post = Post.builder()
                .title("해성맨")
                .content("반포자이")
                .build();
        postRepository.save(post);

        // expected
        assertThatThrownBy(() ->
                postService.delete(post.getId() + 1)).isInstanceOf(PostNotFound.class);
    }

    @Test
    @DisplayName("글 내용 수정 - 존재하지 않는 글")
    void test9() {
        // given
        Post post = Post.builder()
                .title("해성맨")
                .content("반포자이")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("해성맨")
                .content("초가집")
                .build();
        // expected
        assertThatThrownBy(() ->
                postService.edit(post.getId() + 1, postEdit)).isInstanceOf(PostNotFound.class);
    }

}