package com.boka.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.boka.user.model.User;
import org.springframework.data.repository.CrudRepository;

public interface BaseInfoRepository extends MongoRepository<User, String> {

	public User findByMobile(String mobile);
}
