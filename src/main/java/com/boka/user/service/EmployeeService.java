package com.boka.user.service;

import com.boka.common.exception.CommonException;
import com.boka.common.exception.ExceptionCode;
import com.boka.user.dto.UserTO;
import com.boka.user.model.Employee;
import com.boka.user.repository.BaseInfoRepository;
import com.boka.user.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

        Employee item = employeeRepository.findByEmpIdAndShop(user.getShop().getId(), user.getEmpId());
        if (item != null ) {
            throw new CommonException(ExceptionCode.EMPID_EXISTS);
        }
        emp.setEmpId(user.getEmpId());
        emp.setSalary(user.getSalary());
        emp.setAvatar(user.getAvatar());
        emp.setProfession(user.getProfession());
        emp.setShop(user.getShop());
        String empSerial = desktopService.acceptByShop(emp);
        if (empSerial == null){
            throw new CommonException(ExceptionCode.DATA_NOT_EXISTS);
        }else {
            emp.setEmpSerial(empSerial);
        }

        employeeRepository.updateAccept(emp);
    }

    public void refuseByShop(UserTO user) {
        Employee emp = new Employee();
        emp.setId(user.getId());
        emp.setMobile(user.getMobile());
        emp.setShop(user.getShop());
        desktopService.refuseByShop(emp);
        if (!desktopService.refuseByShop(emp))
            throw new CommonException(ExceptionCode.DATA_NOT_EXISTS);

        employeeRepository.updateRefuse(user.getId());
    }

    public List<Employee> getShopEmployee(String id, String pid, String keyword) {
        return employeeRepository.findByShopAndProfession(id, pid, keyword);
    }
}
