package com.boka.user.service;

import com.boka.user.dto.UserTO;
import com.boka.user.repository.BaseInfoRepository;
import com.boka.user.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("employeeService")
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public void acceptByShop(UserTO user) {
        employeeRepository.updateAccept(user.getId());
    }

    public void refuseByShop(UserTO user) {
        employeeRepository.updateRefuse(user.getId());
    }
}
