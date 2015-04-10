package com.boka.user.repository;

import com.boka.user.model.EmployeeLeave;

import java.util.List;

public interface EmployeeLeaveRepositoryAdvance {

	public EmployeeLeave findByShopAndEmp(String shopId, String userId);

    public List<EmployeeLeave> findByShop(String id, String keyword, int page);
}
