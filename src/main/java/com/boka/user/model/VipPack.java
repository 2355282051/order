package com.boka.user.model;

import java.util.Date;

/**
 * Created by boka on 15-3-11.
 * VIP套餐
 */
public class VipPack {

    private String vipPackId; //编号
    private String vipPackName; // 名称
    private String vipPackDesc; // 描述
    private int month; // 月数
    private double price; // 价格
    private double pay; // 支付价格
    private double discount; // 折扣
    private Date createDate;
    private Date updateDate;

    public String getVipPackId() {
        return vipPackId;
    }

    public void setVipPackId(String vipPackId) {
        this.vipPackId = vipPackId;
    }

    public String getVipPackName() {
        return vipPackName;
    }

    public void setVipPackName(String vipPackName) {
        this.vipPackName = vipPackName;
    }

    public String getVipPackDesc() {
        return vipPackDesc;
    }

    public void setVipPackDesc(String vipPackDesc) {
        this.vipPackDesc = vipPackDesc;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
