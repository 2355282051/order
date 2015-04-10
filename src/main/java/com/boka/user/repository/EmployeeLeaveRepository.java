package com.boka.user.repository;

import com.boka.user.model.Employee;
import com.boka.user.model.EmployeeLeave;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeLeaveRepository extends MongoRepository<EmployeeLeave, String>, EmployeeLeaveRepositoryAdvance {

}
