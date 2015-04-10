package com.boka.user.service;

import com.boka.common.exception.CommonException;
import com.boka.common.exception.ExceptionCode;
import com.boka.common.util.Assert;
import com.boka.user.constant.StatusConstant;
import com.boka.user.dto.UserTO;
import com.boka.user.model.Employee;
import com.boka.user.model.EmployeeLeave;
import com.boka.user.repository.BaseInfoRepository;
import com.boka.user.repository.EmployeeLeaveRepository;
import com.boka.user.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("employeeService")
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeLeaveRepository employeeLeaveRepository;

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

        if (!desktopService.editUser(bean)) {
            throw new CommonException(ExceptionCode.DATA_NOT_EXISTS);
        }

        employeeRepository.save(bean);

    }

    public Employee getEmployeeInfo(String id) {
        Employee bean = employeeRepository.findOne(id);
        if (bean == null) {
            throw new CommonException(ExceptionCode.DATA_NOT_EXISTS);
        }
        return bean;
    }

    public List<Employee> getShopAcceptEmployee(String id, int status) {
        return employeeRepository.findByShopAndAccept(id, status);
    }

    public void employeeLeave(String id) {
        Employee emp = employeeRepository.findOne(id);
        EmployeeLeave empLeave = employeeLeaveRepository.findByShopAndEmp(emp.getShop().getId(), id);
        if (empLeave != null) {
            throw new CommonException(ExceptionCode.DATA_NOT_EXISTS);
        }
        desktopService.leave(emp);
        employeeRepository.updateRefuse(id);
        empLeave = new EmployeeLeave();
        empLeave.setShop(emp.getShop());
        empLeave.setEmp(emp);
        employeeLeaveRepository.save(empLeave);
    }

    public void addEmployee(Employee emp) {
        emp.setAcceptStatus(StatusConstant.TRUE);
        String id = desktopService.addUser(emp);
        if (Assert.isNull(id)) {
            throw new CommonException(ExceptionCode.DATA_NOT_EXISTS);
        }
        emp.setId(id);
        employeeRepository.save(emp);
    }

    public EmployeeLeave getEmployeeLeave(String id) {
        return employeeLeaveRepository.findOne(id);
    }

    public List<EmployeeLeave> getEmployeeLeaveList(String id, String keyword, int page) {
        return employeeLeaveRepository.findByShop(id, keyword, page);
    }
}
