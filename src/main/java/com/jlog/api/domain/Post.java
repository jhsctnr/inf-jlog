package com.jlog.api.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Lob
    private String contents;
    private String createdBy;
    private LocalDateTime createdAt;

    @Builder
    public Post(String title, String contents, String createdBy) {
        this.title = title;
        this.contents = contents;
        this.createdBy = createdBy;
        this.createdAt = LocalDateTime.now();
    }

    public PostEditor.PostEditorBuilder toEditor() {
        return PostEditor.builder()
                .title(title)
                .contents(contents)
                .createdBy(createdBy);
    }

    public void edit(PostEditor postEditor) {
        title = postEditor.getTitle();
        contents = postEditor.getContents();
    }
}
