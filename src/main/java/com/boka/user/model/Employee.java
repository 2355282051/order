package com.boka.user.model;

import com.boka.common.constant.ProductType;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_info")
public class Employee extends Designer {

    //职业
    private Profession profession;
    //真实姓名
    private String realName;
    //管理员状态
    private Integer adminStatus;
    //重置密码状态
    private Integer resetStatus;
    // 接受加入状态
    private int acceptStatus;
    // 员工序列号
    private String empSerial;
    // 月薪
    private Double salary;

    public Employee() {
        product = ProductType.FZONE;
        regProduct = ProductType.DESKTOP;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Integer getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(Integer adminStatus) {
        this.adminStatus = adminStatus;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getResetStatus() {
        return resetStatus;
    }

    public void setResetStatus(Integer resetStatus) {
        this.resetStatus = resetStatus;
    }

    public int getAcceptStatus() {
        return acceptStatus;
    }

    public void setAcceptStatus(int acceptStatus) {
        this.acceptStatus = acceptStatus;
    }

    public String getEmpSerial() {
        return empSerial;
    }

    public void setEmpSerial(String empSerial) {
        this.empSerial = empSerial;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
