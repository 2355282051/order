package com.boka.user.repository;

import com.boka.user.model.Employee;

public interface EmployeeRepositoryAdvance {

	public Employee findByMobile(String mobile, String product);
}
