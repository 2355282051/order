package com.boka.user.repository.impl;

import com.boka.user.constant.StatusConstant;
import com.boka.user.model.User;
import com.boka.user.repository.BaseInfoRepositoryAdvance;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class BaseInfoRepositoryImpl implements BaseInfoRepositoryAdvance {

	@Autowired
	private MongoOperations ops;


	@Override
	public User findByMobile(String mobile) {
		Query query = new Query(Criteria.where("mobile").is(mobile).and("activatedStatus").gte(StatusConstant.inactive));
		return ops.findOne(query, User.class);
	}

	@Override
	public User findByQqId(String qqId) {
		Query query = new Query(Criteria.where("qqId").is(qqId).and("activatedStatus").gte(StatusConstant.inactive));
		return ops.findOne(query, User.class);
	}

	@Override
	public User findByWechatId(String wechatId) {
		Query query = new Query(Criteria.where("wechatId").is(wechatId).and("activatedStatus").gte(StatusConstant.inactive));
		return ops.findOne(query, User.class);
	}

	@Override
	public void updateBindUser(User user) {
		Query query = new Query(Criteria.where("_id").is(new ObjectId(user.getId())));
		Update update = new Update().set("activatedStatus", user.getActivatedStatus());
		ops.updateFirst(query, update, User.class);
	}
}
