package com.boka.user.repository.impl;

import com.boka.user.constant.StatusConstant;
import com.boka.user.model.Employee;
import com.boka.user.repository.EmployeeRepositoryAdvance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class EmployeeRepositoryImpl implements EmployeeRepositoryAdvance {

	@Autowired
	private MongoOperations ops;


	@Override
	public Employee findByMobile(String mobile, String product) {
		Query query = new Query(Criteria.where("mobile").is(mobile).and("product").is(product).and("activatedStatus").gte(StatusConstant.inactive));
		//设置显示字段
		query.fields().include("id");
		query.fields().include("avatar");
		query.fields().include("activatedStatus");
		query.fields().include("mobile");
		query.fields().include("name");
		query.fields().include("sex");
		query.fields().include("expireDate");
		query.fields().include("adminStatus");
		query.fields().include("shop.id");
		query.fields().include("shop.name");
		return ops.findOne(query, Employee.class);
	}
}
