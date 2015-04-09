package com.boka.user.service;

import com.boka.common.exception.CommonException;
import com.boka.common.exception.ExceptionCode;
import com.boka.common.util.Assert;
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

    public List<Employee> getShopEmployee(String id, String pid, String keyword, int page) {
        return employeeRepository.findByShopAndProfession(id, pid, keyword, page);
    }

    public void edit(Employee emp) {
        Employee bean = employeeRepository.findOne(emp.getId());
        if (bean == null) {
            throw new CommonException(ExceptionCode.DATA_NOT_EXISTS);
        }
        if(emp.getName() != null) {
            bean.setName(emp.getName());
        }
        if(emp.getEmpId() != null) {
            bean.setEmpId(emp.getEmpId());
        }
        if(emp.getRealName() != null) {
            bean.setRealName(emp.getRealName());
        }
        if(emp.getProfession() != null) {
            bean.setProfession(emp.getProfession());
        }
        if(emp.getSalary() != null) {
            bean.setSalary(emp.getSalary());
        }
        if(emp.getSex() != 0) {
            bean.setSex(emp.getSex());
        }
        if(emp.getAvatar() != null) {
            bean.setAvatar(emp.getAvatar());
        }
        if(emp.getAvatar() != null) {
            bean.setAvatar(emp.getAvatar());
        }
        if (emp.getReserveInfo() != null && emp.getReserveInfo().getStatus() != null) {
            bean.getReserveInfo().setStatus(emp.getReserveInfo().getStatus());
        }
        if (emp.getReserveInfo() != null && emp.getReserveInfo().getStartTime() != null) {
            bean.getReserveInfo().setStartTime(emp.getReserveInfo().getStartTime());
        }
        if (emp.getReserveInfo() != null && emp.getReserveInfo().getEndTime() != null) {
            bean.getReserveInfo().setEndTime(emp.getReserveInfo().getEndTime());
        }
        if (emp.getReserveInfo() != null && emp.getReserveInfo().getInterval() != null && emp.getReserveInfo().getInterval() != 0) {
            bean.getReserveInfo().setInterval(emp.getReserveInfo().getInterval());
        }

        employeeRepository.save(bean);

        //TODO 同步老系统

    }

    public Employee getEmployeeInfo(String id) {
        Employee bean = employeeRepository.findOne(id);
        if (bean == null) {
            throw new CommonException(ExceptionCode.DATA_NOT_EXISTS);
        }
        return bean;
    }

    public List<Employee> getShopAcceptEmployee(String id, String status) {
        return employeeRepository.findByShopAndAccept(id, status);
    }

    public void employeeLeave(String id) {
        employeeRepository.updateRefuse(id);
        //TODO 同步老系统
    }

    public void addEmployee(Employee emp) {
        String id = desktopService.addUser(emp);
        if (Assert.isNull(id)) {
            throw new CommonException(ExceptionCode.DATA_NOT_EXISTS);
        }
        emp.setId(id);
        employeeRepository.save(emp);
    }
}
