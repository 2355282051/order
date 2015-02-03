package com.boka.user.repository;

import com.boka.user.model.Honour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HonourRepository extends MongoRepository<Honour, String>, HonourRepositoryAdvance {

    public Page<Honour> findByDesignerId(String desiginerId, Pageable page);


}
