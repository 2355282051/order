package com.boka.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.boka.user.model.User;

public interface BaseInfoRepository extends MongoRepository<User, String>, BaseInfoRepositoryAdvance {

    public User findByMobile(String mobile);

    public User findByQqId(String qqId);

}
