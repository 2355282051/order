package com.boka.user.service;

import com.boka.common.exception.CommonException;
import com.boka.common.exception.ExceptionCode;
import com.boka.user.dto.UserTO;
import com.boka.user.model.Employee;
import com.boka.user.repository.BaseInfoRepository;
import com.boka.user.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("employeeService")
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DesktopService desktopService;

    public void acceptByShop(UserTO user) {
        Employee emp = employeeRepository.findOne(user.getId());
        if (emp == null)
            throw new CommonException(ExceptionCode.DATA_NOT_EXISTS);

        emp.setEmpId(user.getEmpId());
        emp.setSalary(user.getSalary());
        emp.setAvatar(user.getAvatar());
        emp.setProfession(user.getProfession());
        emp.setShop(user.getShop());
        if (!desktopService.acceptByShop(emp))
            throw new CommonException(ExceptionCode.DATA_NOT_EXISTS);

        employeeRepository.updateAccept(emp);
    }

    public void refuseByShop(UserTO user) {
        Employee emp = new Employee();
        emp.setId(user.getId());
        emp.setShop(user.getShop());
        desktopService.refuseByShop(emp);
        if (!desktopService.refuseByShop(emp))
            throw new CommonException(ExceptionCode.DATA_NOT_EXISTS);

        employeeRepository.updateRefuse(user.getId());
    }
}
