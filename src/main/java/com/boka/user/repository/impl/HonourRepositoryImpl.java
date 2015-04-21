package com.boka.user.repository.impl;

import com.boka.user.model.Honour;
import com.boka.user.repository.HonourRepositoryAdvance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


public class HonourRepositoryImpl implements HonourRepositoryAdvance {

    @Autowired
    private MongoOperations ops;


    @Override
    public long countHonourByDesigner(String designerId) {
        Query query = new Query(Criteria.where("designerId").is(designerId));
        return ops.count(query, Honour.class);
    }
}
