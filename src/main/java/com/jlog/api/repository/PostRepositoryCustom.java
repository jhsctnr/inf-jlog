package com.jlog.api.repository;

import com.jlog.api.domain.Post;
import com.jlog.api.request.PostSearch;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);

    Optional<Post> getPost(Long id);
}
