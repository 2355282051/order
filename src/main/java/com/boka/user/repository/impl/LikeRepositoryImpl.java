package com.boka.user.repository.impl;

import com.boka.user.model.Like;
import com.boka.user.repository.LikeRepositoryAdvance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class LikeRepositoryImpl implements LikeRepositoryAdvance {

	@Autowired
	private MongoOperations ops;

	@Override
	public List<Like> findInIds(String userId, List<String> ids) {
		Query query = new Query(Criteria.where("designerId").in(ids).andOperator(Criteria.where("userId").is(userId)));
		return ops.find(query, Like.class);
	}

	@Override
	public Like findById(String userId, String id) {
		Query query = new Query(Criteria.where("designerId").in(id).andOperator(Criteria.where("userId").is(userId)));
		return ops.findOne(query, Like.class);
	}

}
