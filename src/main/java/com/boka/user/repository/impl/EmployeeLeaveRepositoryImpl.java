package com.boka.user.repository.impl;

import com.boka.common.constant.PageConstant;
import com.boka.common.util.Assert;
import com.boka.user.model.EmployeeLeave;
import com.boka.user.repository.EmployeeLeaveRepositoryAdvance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class EmployeeLeaveRepositoryImpl implements EmployeeLeaveRepositoryAdvance {

	@Autowired
	private MongoOperations ops;

    @Override
    public EmployeeLeave findByShopAndEmp(String shopId, String userId) {
        Query query = new Query(Criteria.where("shop._id").is(shopId).and("emp._id").is(userId));
        return ops.findOne(query, EmployeeLeave.class);
    }

    @Override
    public List<EmployeeLeave> findByShop(String id, String keyword, int page) {
        Query query = new Query(Criteria.where("shop._id").is(id));
        if (Assert.isNotNull(keyword)) {
            query.addCriteria(Criteria.where("emp.realName").regex(keyword, "i"));
        }
        PageRequest pageRequest = new PageRequest(page -1 , PageConstant.DEFAULT_LIST_SIZE);
        query.with(pageRequest);
        return ops.find(query, EmployeeLeave.class);
    }
}
