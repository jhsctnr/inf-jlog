package com.jlog.api.repository;

import com.jlog.api.domain.Member;
import com.jlog.api.domain.Session;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends CrudRepository<Session, Long> {

    List<Session> findByMemberId(Long id);

}
