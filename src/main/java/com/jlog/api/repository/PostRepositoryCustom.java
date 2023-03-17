package com.jlog.api.repository;

import com.jlog.api.domain.Post;
import com.jlog.api.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
