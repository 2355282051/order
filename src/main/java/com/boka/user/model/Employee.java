package com.boka.user.model;

import com.boka.common.constant.ProductType;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_info")
public class Employee extends Designer {

    //职业
    private int profession;
    //真实姓名
    private String realName;
    //管理员状态
    private Integer adminStatus;

    public Employee() {
        product = ProductType.FZONE;
        regProduct = ProductType.DESKTOP;
    }

    public int getProfession() {
        return profession;
    }

    public void setProfession(int profession) {
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
}
