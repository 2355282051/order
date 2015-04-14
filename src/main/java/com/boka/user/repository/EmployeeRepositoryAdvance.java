package com.boka.user.repository;

import com.boka.user.model.Employee;

import java.util.List;

public interface EmployeeRepositoryAdvance {

	public Employee findByMobile(String mobile, String product);

    public void updateRefuse(String id);

    public void updateAccept(Employee emp);

    public Employee findByEmpIdAndShop(String id, String empId);

    public List<Employee> findByShopAndProfession(String id, String pid, String keyword, int page);

    public List<Employee> findByShopAndAccept(String id, int status);

    public List<Employee> findManagerByShop(String id);
}
