package com.jlog.api.repository;

import com.jlog.api.domain.Post;
import com.jlog.api.request.PostSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static com.jlog.api.domain.QPost.post;

public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final JdbcTemplate jdbcTemplate;

    public PostRepositoryImpl(JPAQueryFactory jpaQueryFactory, DataSource dataSource) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Post> getList(PostSearch postSearch) {
        return jpaQueryFactory.selectFrom(post)
                .orderBy(post.id.desc())
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                .fetch();
    }

    public Optional<Post> getPost(Long id) {
        return Optional.of(jdbcTemplate.queryForObject("seleCT * \n     from post \n    where id = ?", postRowMapper(), id));
    }

    private RowMapper<Post> postRowMapper() {
        return (rs, rowNum) -> {
            return Post.builder()
                    .title(rs.getString("title"))
                    .contents(rs.getString("contents"))
                    .createdBy(rs.getString("created_by"))
                    .build();
        };
    }
}
