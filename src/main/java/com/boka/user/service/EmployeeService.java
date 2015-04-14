package com.boka.user.service;

import com.alibaba.fastjson.JSON;
import com.boka.common.constant.ProductType;
import com.boka.common.exception.CommonException;
import com.boka.common.exception.ExceptionCode;
import com.boka.common.util.Assert;
import com.boka.common.util.RandomUtil;
import com.boka.user.constant.StatusConstant;
import com.boka.user.dto.UserTO;
import com.boka.user.model.Employee;
import com.boka.user.model.EmployeeLeave;
import com.boka.user.model.ReserveInfo;
import com.boka.user.model.Shop;
import com.boka.user.repository.BaseInfoRepository;
import com.boka.user.repository.EmployeeLeaveRepository;
import com.boka.user.repository.EmployeeRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service("employeeService")
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeLeaveRepository employeeLeaveRepository;

    @Autowired
    private DesktopService desktopService;

    @Autowired
    private ShopService shopService;

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
        //判断员工工号是否存在
        if(employeeRepository.findByEmpIdAndShop(emp.getShop().getId(), emp.getEmpId()) != null) {
            throw new CommonException(ExceptionCode.EMPID_EXISTS);
        }

        //如果手机号已存在,则更新归属,如果已绑过门店,则返回提示
        Employee bean = employeeRepository.findByMobile(emp.getMobile(), ProductType.FZONE);
        if (bean != null) {
            if (bean.getShop() != null && Assert.isNotNull(bean.getShop().getId())) {
                throw new CommonException(ExceptionCode.EMP_BINDED);
            }
            Shop shop = shopService.getShop(emp.getShop().getId());
            emp.setShop(shop);
            bean.setShop(shop);
            bean.setRealName(emp.getRealName());
            bean.setEmpId(emp.getEmpId());
            bean.setSalary(emp.getSalary());
            bean.setProfession(emp.getProfession());
            bean.setAcceptStatus(StatusConstant.TRUE);
            bean.setApplyDate(new Date());
            if (bean.getReserveInfo() == null) {
                ReserveInfo reserveInfo = new ReserveInfo();
                reserveInfo.setStatus(1);
                Calendar start = Calendar.getInstance();
                start.set(Calendar.HOUR_OF_DAY, 10);
                start.set(Calendar.MINUTE, 0);
                start.set(Calendar.SECOND, 0);
                start.set(Calendar.MILLISECOND, 0);
                reserveInfo.setStartTime(start.getTime());
                Calendar end = Calendar.getInstance();
                end.set(Calendar.HOUR_OF_DAY, 22);
                end.set(Calendar.MINUTE, 0);
                end.set(Calendar.SECOND, 0);
                end.set(Calendar.MILLISECOND, 0);
                reserveInfo.setEndTime(end.getTime());
                reserveInfo.setInterval(30);
                reserveInfo.setInAdvanceMin(0);
                reserveInfo.setInAdvanceMax(10);
                bean.setReserveInfo(reserveInfo);
            }
            //同步老系统
            Employee item = desktopService.addUser(emp);
            bean.setEmpSerial(item.getEmpSerial());
            employeeRepository.save(bean);
            return;
        }

        emp.setAcceptStatus(StatusConstant.TRUE);
        Employee item = desktopService.addUser(emp);
        if (Assert.isNull(item.getId()) || Assert.isNull(item.getEmpSerial())) {
            throw new CommonException(ExceptionCode.DATA_NOT_EXISTS);
        }
        emp.setId(item.getId());
        emp.setEmpSerial(item.getEmpSerial());
        emp.setSalt(RandomUtil.randomSalt());
        String secretPassword = DigestUtils.md5Hex(emp.getSalt() + emp.getMobile());
        emp.setPassword(secretPassword);
        ReserveInfo reserveInfo = new ReserveInfo();
        reserveInfo.setStatus(1);
        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, 10);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        reserveInfo.setStartTime(start.getTime());
        Calendar end = Calendar.getInstance();
        end.set(Calendar.HOUR_OF_DAY, 22);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MILLISECOND, 0);
        reserveInfo.setEndTime(end.getTime());
        reserveInfo.setInterval(30);
        reserveInfo.setInAdvanceMin(0);
        reserveInfo.setInAdvanceMax(10);
        emp.setReserveInfo(reserveInfo);
        emp.setResetStatus(1);
        employeeRepository.save(emp);
    }

    public EmployeeLeave getEmployeeLeave(String id) {
        return employeeLeaveRepository.findOne(id);
    }

    public List<EmployeeLeave> getEmployeeLeaveList(String id, String keyword, int page) {
        return employeeLeaveRepository.findByShop(id, keyword, page);
    }

    public void resetPassword(Employee emp) {
        Employee item = employeeRepository.findOne(emp.getId());
        if(item == null) {
            throw new CommonException(ExceptionCode.USER_NOT_EXISTS);
        }
        item.setResetStatus(0);
        item.setUpdateDate(Calendar.getInstance().getTime());
        //MD5加盐
        item.setSalt(RandomUtil.randomSalt());
        item.setPassword(DigestUtils.md5Hex(item.getSalt() + emp.getPassword()));
        employeeRepository.save(item);
    }
}
