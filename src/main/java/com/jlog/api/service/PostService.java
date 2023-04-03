package com.jlog.api.service;

import com.jlog.api.config.data.UserSession;
import com.jlog.api.domain.Member;
import com.jlog.api.domain.Post;
import com.jlog.api.domain.PostEditor;
import com.jlog.api.exception.InvalidSigninInformation;
import com.jlog.api.exception.PostNotFound;
import com.jlog.api.repository.MemberRepository;
import com.jlog.api.repository.PostRepository;
import com.jlog.api.request.PostCreate;
import com.jlog.api.request.PostEdit;
import com.jlog.api.request.PostSearch;
import com.jlog.api.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public void write(PostCreate postCreate, UserSession userSession) {

        Member findMember = memberRepository.findById(userSession.getId())
                .orElseThrow(InvalidSigninInformation::new);

        // postCreate -> Entity
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .contents(postCreate.getContents())
                .createdBy(findMember.getNickname())
                .build();

        postRepository.save(post);
    }

    public PostResponse get(Long id) {
        Post findPost = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        /**
         *                   ResponseClass 를 위한 경우
         * PostController -> WebPostService -> Repository
         *                   외부 서비스와 통신하는 경우
         *                   PostService
         */

        return PostResponse.builder()
                .id(findPost.getId())
                .title(findPost.getTitle())
                .contents(findPost.getContents())
                .build();
    }

    public List<PostResponse> getList(PostSearch postSearch) {
        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();

        PostEditor postEditor = editorBuilder
                .title(postEdit.getTitle())
                .contents(postEdit.getContents())
                .build();

        post.edit(postEditor);
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        postRepository.delete(post);
    }


//    public List<PostResponse> getList() {
//        return postRepository.findAll().stream()
//                .map(PostResponse::new)
//                .collect(Collectors.toList());
//    }

    // 글이 너무 많은 경우 -> 비용이 너무 많이 든다.
    // 글이 -> 100,000,000 -> DB글 모두 조회하는 경우 -> DB가 뻗을 수 있다.
    // DB -> 애플리케이션 서버로 전달하는 시간, 트래픽비용 등이 많이 발생할 수 있다.

//    public List<PostResponse> getList(Pageable pageable) {
//        return postRepository.findAll(pageable).stream()
//                .map(PostResponse::new)
//                .collect(Collectors.toList());
//    }
}
