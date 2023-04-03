package com.jlog.api.domain;

import lombok.Getter;

@Getter
public class PostEditor {

    private final String title;
    private final String contents;
    private final String createdBy;

    public PostEditor(String title, String contents, String createdBy) {
        this.title = title;
        this.contents = contents;
        this.createdBy = createdBy;
    }

    public static PostEditorBuilder builder() {
        return new PostEditorBuilder();
    }

    public static class PostEditorBuilder {
        private String title;
        private String contents;
        private String createdBy;

        PostEditorBuilder() {
        }

        public PostEditorBuilder title(final String title) {
            this.title = title;
            return this;
        }

        public PostEditorBuilder contents(final String contents) {
            this.contents = contents;
            return this;
        }

        public PostEditorBuilder createdBy(final String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public PostEditor build() {
            return new PostEditor(this.title, this.contents, this.createdBy);
        }

        @Override
        public String toString() {
            return "PostEditorBuilder{" +
                    "title='" + title + '\'' +
                    ", contents='" + contents + '\'' +
                    ", createdBy='" + createdBy + '\'' +
                    '}';
        }
    }
}
