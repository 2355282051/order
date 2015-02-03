package com.boka.user.repository.impl;

import com.boka.user.repository.HonourRepositoryAdvance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;


public class HonourRepositoryImpl implements HonourRepositoryAdvance {

    @Autowired
    private MongoOperations ops;



}
