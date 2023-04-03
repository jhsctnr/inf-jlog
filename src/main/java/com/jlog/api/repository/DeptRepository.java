package com.jlog.api.repository;

import com.jlog.api.domain.Dept;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeptRepository extends CrudRepository<Dept, Long> {

    List<Dept> findAllBy();
}
