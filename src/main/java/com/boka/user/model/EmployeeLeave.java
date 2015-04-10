package com.boka.user.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "employee_leave")
public class EmployeeLeave {

    //主键
    @Id
    private String id;
    //门店
    private Shop shop;
    //离职员工
    private Employee emp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Employee getEmp() {
        return emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }
}
