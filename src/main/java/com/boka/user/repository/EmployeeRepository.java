package com.boka.user.repository;

import com.boka.user.model.Designer;
import com.boka.user.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Designer, String>, EmployeeRepositoryAdvance {

}
