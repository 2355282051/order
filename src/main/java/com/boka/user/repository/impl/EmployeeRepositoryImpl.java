package com.boka.user.repository.impl;

import com.boka.common.constant.PageConstant;
import com.boka.common.util.Assert;
import com.boka.user.constant.StatusConstant;
import com.boka.user.model.Employee;
import com.boka.user.repository.EmployeeRepositoryAdvance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepositoryAdvance {

	@Autowired
	private MongoOperations ops;


	@Override
	public Employee findByMobile(String mobile, String product) {
		Query query = new Query(Criteria.where("mobile").is(mobile).and("product").is(product).and("activatedStatus").gte(StatusConstant.inactive));
		return ops.findOne(query, Employee.class);
	}

    @Override
    public void updateRefuse(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().set("acceptStatus",0).set("shop", null).set("empId", null).set("empSerial", null).set("salary", null).set("acceptStatus", 0).set("adminStatus", 0).set("applyDate", null);
        ops.updateFirst(query,update,Employee.class);
    }

    @Override
    public void updateAccept(Employee emp) {
        Query query = new Query(Criteria.where("_id").is(emp.getId()));
        Update update = new Update().set("acceptStatus",1).set("empId", emp.getEmpId()).set("salary", emp.getSalary()).set("avatar", emp.getAvatar()).set("profession", emp.getProfession()).set("empSerial", emp.getEmpSerial());
        ops.updateFirst(query,update,Employee.class);
    }

    @Override
    public Employee findByEmpIdAndShop(String shopId, String empId) {
        Query query = new Query(Criteria.where("shop._id").is(shopId).and("empId").is(empId));
        return ops.findOne(query, Employee.class);
    }

    @Override
    public List<Employee> findByShopAndProfession(String id, String pid, String keyword, int page) {
        Criteria criteria = Criteria.where("shop._id").is(id).and("acceptStatus").ne(StatusConstant.FALSE);
        if (!pid.equals("-1")) {
            criteria.and("profession._id").is(pid);
        }
        Query query = new Query(criteria);
        if (Assert.isNotNull(keyword)) {
            Criteria cr = new Criteria();
            query.addCriteria(cr.orOperator(Criteria.where("realName").regex(keyword, "i"),Criteria.where("mobile").regex(keyword, "i"),Criteria.where("empId").regex(keyword, "i")));
        }
        Pageable pageable = new PageRequest(page-1, PageConstant.DEFAULT_LIST_SIZE);
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
        Sort sort = new Sort(order);
        query.with(pageable).with(sort);
        return ops.find(query, Employee.class);
    }

    @Override
    public List<Employee> findByShopAndAccept(String id, int status, int page) {
        Query query = new Query(Criteria.where("shop._id").is(id).and("acceptStatus").is(status));
        Pageable pageable = new PageRequest(page-1, PageConstant.DEFAULT_LIST_SIZE);
        query.with(pageable);
        return ops.find(query, Employee.class);
    }

    @Override
    public List<Employee> findManagerByShop(String id) {
        Query query = new Query(Criteria.where("shop._id").is(id).and("profession.name").in("店长","总经理"));
        return ops.find(query, Employee.class);
    }
}
