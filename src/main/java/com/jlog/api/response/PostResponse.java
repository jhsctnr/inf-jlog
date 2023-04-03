package com.jlog.api.response;

import com.jlog.api.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 서비스 정책에 맞는 클래스
 */
@Getter
public class PostResponse {

    private final Long id;
    private final String title;
    private final String contents;
    private final String createdBy;
    private final LocalDateTime createdAt;

    // 생성자 오버로딩
    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.createdBy = post.getCreatedBy();
        this.createdAt = post.getCreatedAt();
    }

    @Builder
    public PostResponse(Long id, String title, String contents, String createdBy, LocalDateTime createdAt) {
        this.id = id;
//        this.title = title.substring(0, Math.min(title.length(), 10));
        this.title = title;
        this.contents = contents;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

}
