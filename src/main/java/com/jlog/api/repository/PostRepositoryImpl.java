package com.jlog.api.repository;

import com.jlog.api.domain.Post;
import com.jlog.api.request.PostSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.jlog.api.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Post> getList(PostSearch postSearch) {
        return jpaQueryFactory.selectFrom(post)
                .orderBy(post.id.desc())
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .fetch();
    }
}
