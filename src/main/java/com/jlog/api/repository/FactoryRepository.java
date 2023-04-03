package com.jlog.api.repository;

import com.jlog.api.domain.Factory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FactoryRepository extends CrudRepository<Factory, Long> {
    List<Factory> findAllBy();
}
