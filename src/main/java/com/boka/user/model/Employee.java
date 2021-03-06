package com.boka.user.model;

import com.boka.common.constant.ProductType;
import com.boka.common.constant.URLConstant;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "user_info")
public class Employee extends Designer {

    //职业
    private Profession profession;
    //真实姓名
    private String realName;
    //管理员状态
    private Integer adminStatus;
    // 接受加入状态
    private int acceptStatus;
    // 员工序列号
    private String empSerial;
    // 月薪
    private Double salary;
    // 申请加入门店时间
    private Date applyDate;

    public Employee() {
        product = ProductType.FZONE;
        regProduct = ProductType.DESKTOP;
    }

    @Override
    public void setAvatar(String avatar) {
        if (avatar != null && avatar.indexOf("http") == -1) {
            this.avatar = URLConstant.HEADER_URL + avatar;
        } else {
            this.avatar = avatar;
        }
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

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }
}
